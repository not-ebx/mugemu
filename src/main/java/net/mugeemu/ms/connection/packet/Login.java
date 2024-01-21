package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.client.Account;
import net.mugeemu.ms.client.User;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.connection.db.daos.UserDAO;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.enums.LoginType;
import net.mugeemu.ms.ServerStatus;
import net.mugeemu.ms.handlers.header.OutHeader;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.util.Randomizer;
import net.mugeemu.ms.util.container.Tuple;
import net.mugeemu.ms.world.Channel;
import net.mugeemu.ms.Server;
import net.mugeemu.ms.world.World;

import java.util.*;

/**
 * Created by Tim on 2/28/2017.
 */
public class Login {

    public static OutPacket sendConnect(byte[] siv, byte[] riv) {
        OutPacket oPacket = new OutPacket();

        // version (short) + MapleString (short + char array size) + local IV (int) + remote IV (int) + locale (byte)
        // 0xE
        oPacket.encodeShort((short) 14);
        oPacket.encodeShort(ServerConstants.VERSION);
        oPacket.encodeString(ServerConstants.MINOR_VERSION);
        oPacket.encodeArr(siv);
        oPacket.encodeArr(riv);
        oPacket.encodeByte(ServerConstants.LOCALE);
        //oPacket.encodeByte(false);
        return oPacket;
    }

    public static OutPacket sendAliveReq() {
        return new OutPacket(OutHeader.ALIVE_REQ.getValue());
    }

    public static OutPacket sendAuthServer(boolean useAuthServer) {
        OutPacket outPacket = new OutPacket(OutHeader.AUTH_SERVER.getValue());
        outPacket.encodeByte(useAuthServer);
        return outPacket;
    }

    public static OutPacket setHotFix(boolean incorrectHotFix) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_HOT_FIX.getValue());
        outPacket.encodeByte(incorrectHotFix);
        return outPacket;
    }

    public static OutPacket setHotFix(ArrayList<Byte> encryptedHotFixLen, byte[] dataWzHash, byte[] hotFix) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_HOT_FIX.getValue());
        for(Byte lenByte : encryptedHotFixLen)  {
            outPacket.encodeByte(lenByte);
        }
        outPacket.encodeArr(dataWzHash);
        outPacket.encodeArr(hotFix);
        return outPacket;
    }

    public static OutPacket checkPasswordResult(boolean success, LoginType msg, User user) {
        OutPacket outPacket = new OutPacket(OutHeader.CHECK_PASSWORD_RESULT.getValue());

        if (success) {
            outPacket.encodeByte(LoginType.Success.getValue());
            outPacket.encodeByte(0);
            outPacket.encodeInt(user.getId()); //
            outPacket.encodeByte(user.getGender()); //
            outPacket.encodeByte(user.getGradeCode() > 0); //ios it gm
            outPacket.encodeByte(0); //
            outPacket.encodeByte(user.getGradeCode() > 0); //
            outPacket.encodeString(user.getName());
            outPacket.encodeByte(3); // 0 for new accs
            outPacket.encodeByte(0); // Quiet ban
            outPacket.encodeLong(0); // Quiet ban time
            outPacket.encodeFT(user.getCreationDate()); // Create Date
            outPacket.encodeInt(4); // Something related to "SElect the world you want to play in"
            outPacket.encodeByte(1); // Pin Disabled
            outPacket.encodeByte(
                user.getPicStatus().getVal()
            ); //PIC, 2=none, gotta make it work afterwards
            outPacket.encodeLong(Randomizer.nextLong());
        } else if (msg == LoginType.Blocked) {
            outPacket.encodeByte(msg.getValue());
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
            outPacket.encodeByte(0); // nReason
            outPacket.encodeFT(user.getBanExpireDate());
        } else {
            outPacket.encodeByte(msg.getValue());
            outPacket.encodeByte(0); // these two aren't in ida, wtf
            outPacket.encodeInt(0);
        }

        return outPacket;
    }

    public static OutPacket checkPasswordResultForBan(User user) {
        OutPacket outPacket = new OutPacket(OutHeader.CHECK_PASSWORD_RESULT);

        outPacket.encodeByte(LoginType.BlockedNexonID.getValue());
        outPacket.encodeByte(0);
        outPacket.encodeInt(0);
        outPacket.encodeByte(0);
        outPacket.encodeFT(user.getBanExpireDate());

        return outPacket;
    }

    public static OutPacket sendWorldInformation(World world, Set<Tuple<Position, String>> stringInfos) {
        // CLogin::OnWorldInformation
        OutPacket outPacket = new OutPacket(OutHeader.WORLD_INFORMATION);

        outPacket.encodeByte(world.getWorldId());
        outPacket.encodeString(world.getName());
        outPacket.encodeByte(world.getWorldState());
        outPacket.encodeString(world.getWorldEventDescription());
        outPacket.encodeShort(world.getWorldEventEXP_WSE());
        outPacket.encodeShort(world.getWorldEventDrop_WSE());
        outPacket.encodeByte(world.isCharCreateBlock());
        outPacket.encodeByte(world.getChannels().size());
        for (Channel c : world.getChannels()) {
            outPacket.encodeString(String.format("%s-%d",world.getName(), c.getChannelId()));
            outPacket.encodeInt(c.getGaugePx());
            outPacket.encodeByte(c.getWorldId());
            outPacket.encodeByte(c.getChannelId());
            outPacket.encodeByte(c.isAdultChannel());
        }
        if (stringInfos == null) {
            outPacket.encodeShort(0);
        } else {
            outPacket.encodeShort(stringInfos.size());
            for (Tuple<Position, String> stringInfo : stringInfos) {
                outPacket.encodePosition(stringInfo.getLeft());
                outPacket.encodeString(stringInfo.getRight());
            }
        }
        outPacket.encodeInt(0); // some offset
        return outPacket;
    }

    public static OutPacket sendWorldInformationEnd() {
        OutPacket outPacket = new OutPacket(OutHeader.WORLD_INFORMATION);

        outPacket.encodeInt(255);

        return outPacket;
    }

    public static OutPacket sendAccountInfo(User user) {
        OutPacket outPacket = new OutPacket(OutHeader.ACCOUNT_INFO_RESULT);

        outPacket.encodeByte(0); // Operation

        // When operation is 0
        outPacket.encodeInt(user.getId()); //
        outPacket.encodeByte(user.getGender()); //
        outPacket.encodeByte(user.getGradeCode() > 0); //ios it gm
        outPacket.encodeShort(2); // why 2 lol
        outPacket.encodeByte(user.getGradeCode() > 0); // Admin again :)
        outPacket.encodeString(user.getName());

        outPacket.encodeByte(3); // 0 for new accs
        outPacket.encodeByte(0); // Quiet ban
        outPacket.encodeLong(0); // Quiet ban time
        outPacket.encodeFT(user.getCreationDate()); // Create Date
        outPacket.encodeInt(4); // Something related to "SElect the world you want to play in"
        outPacket.encodeLong(Randomizer.nextLong());
        outPacket.encodeByte(0); //PIC, 2=none, gotta make it work afterwards


        return outPacket;
    }

    public static OutPacket sendServerStatus(byte worldId) {
        OutPacket outPacket = new OutPacket(OutHeader.SERVER_STATUS.getValue());
        World world = null;
        for (World w : Server.getInstance().getWorlds()) {
            if (w.getWorldId() == worldId) {
                world = w;
            }
        }
        if (world != null && !world.isFull()) {
            outPacket.encodeByte(world.getStatus().getValue());
        } else {
            outPacket.encodeByte(ServerStatus.BUSY.getValue());
        }
        outPacket.encodeByte(0);

        return outPacket;
    }

    public static OutPacket selectWorldResult(User user, Account account, byte code) {
        UserDAO udao = new UserDAO();
        OutPacket outPacket = new OutPacket(OutHeader.SELECT_WORLD_RESULT);
        List<Char> chars = udao.getSelectCharacterFromAccount(account);
        outPacket.encodeByte(code);
        outPacket.encodeByte(chars != null ? chars.size() : 0);
        if(chars != null) {
            for (Char chr : chars) {
                chr.getAvatarData().encode(outPacket);
                // v111 has family? Dont think so xd
                outPacket.encodeByte(false); // family stuff, deprecated (v61 = &v2->m_abOnFamily.a[v59];)
                boolean hasRanking = chr.getRanking() != null && !JobConstants.isGmJob(chr.getJob());
                //outPacket.encodeByte(0);
                outPacket.encodeByte(hasRanking);
                if (hasRanking) {
                    chr.getRanking().encode(outPacket);
                }
            }
        }
        //outPacket.encodeByte(user.getPicStatus().getVal()); // bLoginOpt
        outPacket.encodeByte(2); // bLoginOpt
        outPacket.encodeByte(1);
        outPacket.encodeInt(user.getCharacterSlots());
        outPacket.encodeInt(0); // buying char slots

        return outPacket;
    }

    public static OutPacket checkDuplicatedIDResult(String name, byte code) {
        OutPacket outPacket = new OutPacket(OutHeader.CHECK_DUPLICATED_ID_RESULT);

        outPacket.encodeString(name);
        outPacket.encodeByte(code);

        return outPacket;
    }

    public static OutPacket createNewCharacterResult(LoginType type, Char c) {
        OutPacket outPacket = new OutPacket(OutHeader.CREATE_NEW_CHARACTER_RESULT);

        outPacket.encodeByte(type.getValue());
        if (type == LoginType.Success) {
            c.getAvatarData().encode(outPacket);
        }

        return outPacket;
    }

    public static OutPacket sendAuthResponse(int response) {
        OutPacket outPacket = new OutPacket(OutHeader.PRIVATE_SERVER_PACKET);

        outPacket.encodeInt(response);

        return outPacket;
    }

    public static OutPacket selectCharacterResult(LoginType loginType, byte errorCode, int port, int characterId) {
        OutPacket outPacket = new OutPacket(OutHeader.SELECT_CHARACTER_RESULT);

        outPacket.encodeByte(loginType.getValue());
        outPacket.encodeByte(errorCode);

        if (loginType == LoginType.Success) {
            //byte[] server = new byte[]{8, 31, 99, ((byte) 141)};
            //TODO: Must take ip from ServerConfig / Constants
            //byte[] server = new byte[]{(byte) 192, (byte)168, 1, (byte)167};
            outPacket.encodeArr(ServerConstants.SERVER_IP);
            outPacket.encodeShort(port);

            outPacket.encodeInt(characterId);
            outPacket.encodeByte(1);
            outPacket.encodeInt(0);
            outPacket.encodeByte(1);
            outPacket.encodeShort(0);
            outPacket.encodeShort(0);
        }
        return outPacket;
    }

    public static OutPacket sendDeleteCharacterResult(int charId, LoginType loginType) {
        OutPacket outPacket = new OutPacket(OutHeader.DELETE_CHARACTER_RESULT);

        outPacket.encodeInt(charId);
        outPacket.encodeByte(loginType.getValue());


        return outPacket;
    }

    public static OutPacket sendRecommendWorldMessage(int nWorldID, String nMsg) {
        OutPacket oPacket = new OutPacket(OutHeader.RECOMMENDED_WORLD_MESSAGE);
        oPacket.encodeByte(!nMsg.isEmpty());
        oPacket.encodeInt(nWorldID);
        oPacket.encodeString(nMsg);
        return oPacket;
    }

    public static OutPacket changePicResponse(LoginType result) {
        OutPacket outPacket = new OutPacket(OutHeader.CHANGE_SPW_RESULT);
        outPacket.encodeByte(result.getValue());
        return outPacket;
    }
}