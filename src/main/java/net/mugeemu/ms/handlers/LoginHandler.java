package net.mugeemu.ms.handlers;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.connection.db.DatabaseManager;
import net.mugeemu.ms.connection.db.daos.UserDAO;
import net.mugeemu.ms.connection.packet.Login;
import net.mugeemu.ms.connection.packet.WvsContext;
import net.mugeemu.ms.constants.GameConstants;
import net.mugeemu.ms.constants.ItemConstants;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.handlers.header.InHeader;
import net.mugeemu.ms.handlers.header.OutHeader;
import net.mugeemu.ms.ServerConfig;
import net.mugeemu.ms.client.Account;
import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.User;
import net.mugeemu.ms.client.character.BroadcastMsg;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.character.CharacterStat;
import net.mugeemu.ms.client.character.items.BodyPart;
import net.mugeemu.ms.client.character.items.Equip;
import net.mugeemu.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.mugeemu.ms.client.jobs.JobManager;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.enums.CashItemType;
import net.mugeemu.ms.enums.CharNameResult;
import net.mugeemu.ms.enums.LoginType;
import net.mugeemu.ms.loaders.ItemData;
import net.mugeemu.ms.util.FileTime;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.world.World;
import org.apache.log4j.LogManager;
import net.mugeemu.ms.world.Channel;
import net.mugeemu.ms.Server;
import org.hibernate.Hibernate;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.mugeemu.ms.enums.InvType.EQUIPPED;

/**
 * Created on 4/28/2017.
 */
public class LoginHandler {

    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();

    @Handler(op = InHeader.VERSION_VERIFY)
    public static void handlePermissionRequest(Client client, InPacket inPacket) {
        byte locale = inPacket.decodeByte();
        short version = inPacket.decodeShort();
        String minorVersion = inPacket.decodeString(1);
        if (locale != ServerConstants.LOCALE || version != ServerConstants.VERSION) {
            log.info(String.format("Client %s has an incorrect version.", client.getIP()));
            client.close();
        }
        client.write(Login.sendConnect(client.getSendIV(), client.getRecvIV()));
    }

    @Handler(op = InHeader.USE_AUTH_SERVER)
    public static void handleAuthServer(Client client, InPacket inPacket) {
        client.write(Login.sendAuthServer(false));
    }

    @Handler(op = InHeader.APPLIED_HOT_FIX)
    public static void handleAppliedHotFix(Client client, InPacket inPacket) {
        // First 4 bytes of a SHA1 hash of Data.wz, 0's if does not exist
        byte[] appliedHotFix = inPacket.decodeArr(4);
        boolean incorrectHotFix = true;
        File dataWz = new File("resources/Data.wz");
        if (dataWz.exists()) {
            try {
                byte[] hotFix = Files.readAllBytes(dataWz.toPath());
                byte[] dataWzHash = Util.sha1Hash(hotFix);
                if (dataWzHash == null)  {
                    log.error("Data.wz hashing has failed.");
                    incorrectHotFix = true;
                    client.write(Login.setHotFix(incorrectHotFix));
                    return;
                }
                // Only care about the first 4 bytes of the hash
                dataWzHash = Arrays.copyOfRange(dataWzHash, 0, 4);
                if (Arrays.equals(dataWzHash, appliedHotFix))  {
                    incorrectHotFix = false;
                    client.write(Login.setHotFix(incorrectHotFix));
                    return;
                }
                ArrayList<Byte> encryptedHotFixLen = new ArrayList<>();
                int cryptHotFixLen = hotFix.length << 1;
                while (cryptHotFixLen > 0x80) {
                    encryptedHotFixLen.add((byte) ((cryptHotFixLen & 0x7F) | 0x80));
                    cryptHotFixLen = cryptHotFixLen >> 7;
                }
                encryptedHotFixLen.add((byte) cryptHotFixLen);
                client.write(Login.setHotFix(encryptedHotFixLen, dataWzHash, hotFix));
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        client.write(Login.setHotFix(incorrectHotFix));
    }

    @Handler(op = InHeader.PONG)
    public static void handlePong(Client c, InPacket inPacket) {

    }

    @Handler(op = InHeader.CHECK_LOGIN_AUTH_INFO)
    public static void handleCheckLoginAuthInfo(Client c, InPacket inPacket) {
        UserDAO udao = new UserDAO();
        String username = inPacket.decodeString();
        String password = inPacket.decodeString();
        byte[] machineID = inPacket.decodeArr(16);
        boolean success;
        LoginType result;
        //User user = User.getFromDBByName(username);
        User user = udao.getUserByName(username);
        if (user != null) {
            if ("helphelp".equalsIgnoreCase(password)) {
                user.unstuck();
                c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage("Your account is now logged out.")));
            }
            String dbPassword = user.getPassword();
            boolean hashed = Util.isStringBCrypt(dbPassword);
            if (hashed) {
                try {
                    success = BCrypt.checkpw(password, dbPassword);
                } catch (IllegalArgumentException e) { // if password hashing went wrong
                    log.error(String.format("bcrypt check in login has failed! dbPassword: %s; stack trace: %s", dbPassword, e.getStackTrace().toString()));
                    success = false;
                }
            } else {
                success = password.equals(dbPassword);
            }
            result = success ? LoginType.Success : LoginType.IncorrectPassword;
            if (success) {
                if (Server.getInstance().isUserLoggedIn(user)) {
                    success = false;
                    result = LoginType.AlreadyConnected;
                } else if (user.getBanExpireDate() != null && !user.getBanExpireDate().isExpired()) {
                    success = false;
                    result = LoginType.Blocked;
                    String banMsg = String.format("You have been banned. \nReason: %s. \nExpire date: %s",
                            user.getBanReason(), user.getBanExpireDate().toLocalDateTime());
                    c.write(WvsContext.broadcastMsg(BroadcastMsg.popUpMessage(banMsg)));
                } else {
                    if (!hashed) {
                        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(ServerConstants.BCRYPT_ITERATIONS)));
                        // if a user has an assigned pic, hash it
                        if (user.getPic() != null && user.getPic().length() >= 6 && !Util.isStringBCrypt(user.getPic())) {
                            user.setPic(BCrypt.hashpw(user.getPic(), BCrypt.gensalt(ServerConstants.BCRYPT_ITERATIONS)));
                        }
                    }
                    Server.getInstance().addUser(user);
                    c.setUser(user);
                    c.setMachineID(machineID);
                    //TODO: Remove and add on SPW
                    c.setAuthorized(true);
                }
            }
        } else {
            if(ServerConfig.AUTO_REGISTER) {
                User autoRegisteredUser = udao.autoregisterUser(username, password);
                user = udao.getUserByName(autoRegisteredUser.getName());
                Server.getInstance().addUser(autoRegisteredUser);
                c.setUser(autoRegisteredUser);
                c.setMachineID(machineID);
                //TODO: Remove and add on SPW
                c.setAuthorized(true);
                success = true;
                result = LoginType.Success;
            } else {
                result = LoginType.NotRegistered;
                success = false;
            }

        }
        c.write(Login.checkPasswordResult(success, result, user));
    }

    /*
    @Handler(op = InHeader.WORLD_LIST_REQUEST)
    public static void handleWorldListRequest(Client c, InPacket packet) {
        for (World world : Server.getInstance().getWorlds()) {
            c.write(Login.sendWorldInformation(world, null));
        }
        c.write(Login.sendWorldInformationEnd());
        c.write(Login.sendRecommendWorldMessage(ServerConfig.WORLD_ID, ServerConfig.RECOMMEND_MSG));
    }
     */

    @Handler(op = InHeader.WORLD_INFO_REQUEST)
    public static void handleWorldInfoRequest(Client c, InPacket packet) {
        List<World> worlds = Server.getInstance().getWorlds();
        for (World world : worlds) {
            c.write(Login.sendWorldInformation(world, null));
        }
        c.write(Login.sendWorldInformationEnd());
        c.write(Login.sendRecommendWorldMessage(ServerConfig.WORLD_ID, ServerConfig.RECOMMEND_MSG, (short)worlds.size()));
    }

    @Handler(op = InHeader.SERVERSTATUS_REQUEST)
    public static void handleServerStatusRequest(Client c, InPacket inPacket) {
        byte worldId = inPacket.decodeByte();
        c.write(Login.sendServerStatus(worldId));
    }

    @Handler(op = InHeader.WORLD_STATUS_REQUEST)
    public static void handleWorldStatusRequest(Client c, InPacket inPacket) {
        byte worldId = inPacket.decodeByte();
        c.write(Login.sendServerStatus(worldId));
    }

    @Handler(op = InHeader.REDISPLAY_SERVERLIST)
    public static void handleRedisplayServerlist(Client c, InPacket inPacket) {
        handleWorldInfoRequest(c, inPacket);
    }

    @Handler(op = InHeader.SELECT_WORLD)
    public static void handleSelectWorld(Client c, InPacket inPacket) {
        UserDAO udao = new UserDAO();
        byte somethingThatIsTwo = inPacket.decodeByte();
        byte worldId = inPacket.decodeByte();
        byte channel = (byte) (inPacket.decodeByte() + (byte)1);
        byte code = 0; // success code
        User user = c.getUser();


        World world = Server.getInstance().getWorldById(worldId);
        if (world != null && world.getChannelById(channel) != null) {
            Account acc = udao.getAccountFromUserByWorld(user, worldId);
            if (acc == null) {
                acc = udao.createUserAccount(user, worldId);
            }
            c.setAccount(acc);
            c.setWorldId(worldId);
            c.setChannel(channel);
            // Unused in v92 cause it sent before
            //c.write(Login.sendAccountInfo(user));
            c.write(Login.selectWorldResult(c.getUser(), c.getAccount(), code));
            int lanIpQuestionmark = inPacket.decodeInt();
        } else {
            c.write(Login.selectCharacterResult(LoginType.UnauthorizedUser, (byte) 0, 0, 0));
        }
    }

    @Handler(op = InHeader.SELECT_PREVIOUS_WORLD)
    public static void handleReselectWorld(Client c, InPacket inPacket) {
        // Nothing, ignored. Maybe we can return the last world? idk lol
        c.write(Login.sendLastConnectedWorld(0));
    }

    @Handler(op = InHeader.CHECK_DUPLICATE_ID)
    public static void handleCheckDuplicatedID(Client c, InPacket inPacket) {
        String name = inPacket.decodeString();
        CharNameResult code;
        if (!GameConstants.isValidName(name)) {
            code = CharNameResult.Unavailable_Invalid;
        } else {
            code = Char.findCharacterByName(name) == null
                ? CharNameResult.Available
                : CharNameResult.Unavailable_InUse;
        }
        c.write(Login.checkDuplicatedIDResult(name, code.getVal()));
    }

    @Handler(op = InHeader.CREATE_NEW_CHARACTER)
    public static void handleCreateNewCharacter(Client c, InPacket inPacket) {
        UserDAO udao = new UserDAO();
        Account acc = c.getAccount();
        String name = inPacket.decodeString();
        int curSelectedRace = inPacket.decodeInt();
        JobConstants.JobEnum job = JobConstants.LoginJob.getLoginJobById(curSelectedRace).getBeginJob();
        short curSelectedSubJob = inPacket.decodeShort(); // Is db or not, subjobs

        //byte gender = inPacket.decodeByte();
        //byte skin = inPacket.decodeByte();
        //byte itemLength = inPacket.decodeByte();
        byte itemLength = 8; // Guess
        int[] items = new int[itemLength]; // Face, hair, hairColor, skinColor, top, bottom, shoes, weapon
        for (int i = 0; i < itemLength; i++) {
            items[i] = inPacket.decodeInt();
        }
        int face = items[0];
        int hair = items[1] + items[2];
        byte skin = (byte)items[3];
        byte gender = inPacket.decodeByte();
        items = Arrays.copyOfRange(items, 4, items.length);
        CharNameResult code = null;
        if (!ItemData.isStartingItems(items) || skin > ItemConstants.MAX_SKIN || skin < 0
                || face < ItemConstants.MIN_FACE || face > ItemConstants.MAX_FACE
                || hair < ItemConstants.MIN_HAIR || hair > ItemConstants.MAX_HAIR) {
            c.getUser().getOffenseManager().addOffense("Tried to add items unavailable on char creation.");
            code = CharNameResult.Unavailable_CashItem;
        }

        if (!GameConstants.isValidName(name)) {
            code = CharNameResult.Unavailable_Invalid;
        } else if (Char.getFromDBByNameAndWorld(name, acc.getWorldId()) != null) {
            code = CharNameResult.Unavailable_InUse;
        }
        if (code != null) {
            c.write(Login.checkDuplicatedIDResult(name, code.getVal()));
            return;
        }

        Char chr = udao.createCharacter(
            acc,
            name,
            job.getJobId(),
            curSelectedRace,
            curSelectedSubJob,
            gender, skin, face, hair, items
        );

        c.write(Login.createNewCharacterResult(LoginType.Success, chr));
    }

    @Handler(op = InHeader.DELETE_CHARACTER)
    public static void handleDeleteCharacter(Client c, InPacket inPacket) {
        if (c.getAccount() != null && handleCheckSpwRequest(c, inPacket)) {
            int charId = inPacket.decodeInt();
            Account acc = c.getAccount();
            Char chr = acc.getCharById(charId);
            if (chr != null) {
                acc.removeLinkSkillByOwnerID(chr.getId());
                acc.getCharacters().remove(chr);
                DatabaseManager.saveToDB(acc);
                c.write(Login.sendDeleteCharacterResult(charId, LoginType.Success));
            } else {
                c.write(Login.sendDeleteCharacterResult(charId, LoginType.UnauthorizedUser));
            }
        }
    }

    @Handler(op = InHeader.CLIENT_ERROR)
    public static void handleClientError(Client c, InPacket inPacket) {
        c.close();
        if (inPacket.getData().length < 8) {
            log.error(String.format("Error: %s", inPacket));
            return;
        }
        short type = inPacket.decodeShort();
        String type_str = "Unknown?!";
        if (type == 0x01) {
            type_str = "SendBackupPacket";
        } else if (type == 0x02) {
            type_str = "Crash Report";
        } else if (type == 0x03) {
            type_str = "Exception";
        }
        int errortype = inPacket.decodeInt();
        short data_length = inPacket.decodeShort();

        int idk = inPacket.decodeInt();

        short op = inPacket.decodeShort();

        OutHeader opcode = OutHeader.getOutHeaderByOp(op);
        log.error(String.format("[Error %s] (%s / %d) Data: %s", errortype, opcode, op, inPacket));
        if (opcode == OutHeader.TEMPORARY_STAT_SET) {
            for (int i = 0; i < CharacterTemporaryStat.length; i++) {
                int mask = inPacket.decodeInt();
                for (CharacterTemporaryStat cts : CharacterTemporaryStat.values()) {
                    if (cts.getPos() == i && (cts.getVal() & mask) != 0) {
                        log.error(String.format("[Error %s] Contained stat %s", errortype, cts.toString()));
                    }
                }
            }
        } else if (opcode == OutHeader.CASH_SHOP_CASH_ITEM_RESULT) {
            byte cashType = inPacket.decodeByte();
            CashItemType cit = CashItemType.getResultTypeByVal(cashType);
            log.error(String.format("[Error %s] CashItemType %s", errortype, cit == null ? "Unknown" : cit.toString()));
        }
    }

    @Handler(op = InHeader.PRIVATE_SERVER_PACKET)
    public static void handlePrivateServerPacket(Client c, InPacket inPacket) {
        c.write(Login.sendAuthResponse(((int) OutHeader.PRIVATE_SERVER_PACKET.getValue()) ^ inPacket.decodeInt()));
    }

    @Handler(op = InHeader.CHAR_SELECT_NO_PIC)
    public static void handleCharSelectNoPic(Client c, InPacket inPacket) {
        //byte[] nothingTbh = inPacket.decodeArr(2);
        UserDAO udao = new UserDAO();
        int characterId = inPacket.decodeInt();
        String mac = inPacket.decodeString();
        //String somethingElse = inPacket.decodeString();

        Char foundCharacter = udao.getCharacterByIdAndAccount(c.getAccount(), characterId);
        if (c.isAuthorized() && foundCharacter != null) {
            byte channelId = c.getChannel();
            // Set all of the client shit.
            c.setWorldId(c.getWorldId());
            c.setChannel(channelId);
            c.setChr(foundCharacter);
            c.setUser(c.getUser());
            c.getUser().setCurrentChr(foundCharacter);
            c.setAccount(c.getAccount());
            c.getUser().setCurrentAcc(c.getAccount());
            foundCharacter.setClient(c);

            Channel channel = Server.getInstance().getWorldById(c.getWorldId()).getChannelById(channelId);
            Server.getInstance().getWorldById(c.getWorldId()).getChannelById(channelId).addClientInTransfer(channelId, characterId, c);
            c.write(Login.selectCharacterResult(LoginType.Success, (byte) 0, channel.getPort(), characterId));
            return;
        }
        c.write(Login.selectCharacterResult(LoginType.UnauthorizedUser, (byte) 0, 0, 0));
    }

    @Handler(op = InHeader.CHAR_SELECT)
    public static void handleCharSelect(Client c, InPacket inPacket) {
        int characterId = inPacket.decodeInt();
        String name = inPacket.decodeString();
        byte worldId = c.getWorldId();
        byte channelId = c.getChannel();
        Channel channel = Server.getInstance().getWorldById(worldId).getChannelById(channelId);
        if (c.isAuthorized() && c.getAccount().hasCharacter(characterId)) {
            Server.getInstance().getWorldById(worldId).getChannelById(channelId).addClientInTransfer(channelId, characterId, c);
            c.write(Login.selectCharacterResult(LoginType.Success, (byte) 0, channel.getPort(), characterId));
        }
        // if anything is wrong, the 2nd pwd authorizer should return an error
    }


    @Handler(op = InHeader.CHANGE_PIC_REQUEST)
    public static void handleChangePicRequest(Client c, InPacket inPacket) {
        String currentPic = inPacket.decodeString();

        if (BCrypt.checkpw(currentPic, c.getUser().getPic())) {
            String unencryptedPic = inPacket.decodeString();
            if (unencryptedPic.length() < 6) {
                c.write(Login.changePicResponse(LoginType.InsufficientSPW));
            } else if (BCrypt.checkpw(unencryptedPic, c.getUser().getPassword())) {
                c.write(Login.changePicResponse(LoginType.SamePasswordAndSPW));
            } else {
                String pic = BCrypt.hashpw(unencryptedPic, BCrypt.gensalt(ServerConstants.BCRYPT_ITERATIONS));
                c.getUser().setPic(pic);
                // Update in DB
                DatabaseManager.merge(c.getUser());
                c.write(Login.changePicResponse(LoginType.Success));
            }
        } else {
            c.write(Login.changePicResponse(LoginType.IncorrectSPW));
        }
    }

    @Handler(op = InHeader.CHECK_SPW_REQUEST)
    public static boolean handleCheckSpwRequest(Client c, InPacket inPacket) {
        boolean success = false;
        String pic = inPacket.decodeString();
//        int userId = inPacket.decodeInt();
        // after this: 2 strings indicating pc info. Not interested in that rn
        if (BCrypt.checkpw(pic, c.getUser().getPic())) {
            success = true;
        } else {
            c.write(Login.selectCharacterResult(LoginType.IncorrectPassword, (byte) 0, 0, 0));
        }
        c.setAuthorized(success);
        return success;
    }

    @Handler(op = InHeader.EXCEPTION_LOG)
    public static void handleExceptionLog(Client c, InPacket inPacket) {
        String str = inPacket.decodeString();
        log.error("Exception log: " + str);
    }

    @Handler(op = InHeader.WVS_CRASH_CALLBACK)
    public static void handleWvsCrashCallback(Client c, InPacket inPacket){
        if (c != null && c.getChr() != null) {
            c.getChr().setChangingChannel(false);
            c.getChr().logout();
        }
    }
}