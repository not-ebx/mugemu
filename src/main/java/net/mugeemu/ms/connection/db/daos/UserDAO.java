package net.mugeemu.ms.connection.db.daos;

import jakarta.persistence.NoResultException;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.client.Account;
import net.mugeemu.ms.client.User;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.character.CharacterStat;
import net.mugeemu.ms.client.character.avatar.AvatarData;
import net.mugeemu.ms.client.character.items.BodyPart;
import net.mugeemu.ms.client.character.items.Equip;
import net.mugeemu.ms.client.jobs.JobManager;
import net.mugeemu.ms.connection.db.DatabaseManager;
import net.mugeemu.ms.constants.ItemConstants;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.loaders.ItemData;
import net.mugeemu.ms.util.FileTime;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.mugeemu.ms.enums.InvType.EQUIPPED;

public class UserDAO {

    public UserDAO() {}

    public User getUserByName(String username) {
        Session session = DatabaseManager.getSession();
        try {
            Query<User> query = session.createQuery(
                "From User where name = :username",
                User.class
            );

            query.setParameter("username", username);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
    }

    public User autoregisterUser(String username, String password) {
        Session session = DatabaseManager.getSession();
        Transaction tx = session.beginTransaction();
        try {
            User autoRegisteredUser = new User(
                username,
                BCrypt.hashpw(password, BCrypt.gensalt(ServerConstants.BCRYPT_ITERATIONS))
            );

            autoRegisteredUser.setCharacterSlots(5);
            autoRegisteredUser.setBanExpireDate(new FileTime(0));
            autoRegisteredUser.setAge(50);
            session.persist(
                autoRegisteredUser
            );

            tx.commit();

            return autoRegisteredUser;

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
    }

    public Account getAccountFromUserByWorld(User user, int worldId) {
        Account acc = null;
        Session session = DatabaseManager.getSession();
        try {
            Query<Account> query = session.createQuery(
                "From Account where user = :user and worldId = :worldId",
                Account.class
            );

            query.setParameter("user", user);
            query.setParameter("worldId", worldId);

            acc = query.getSingleResult();
            //Hibernate.initialize(acc.getCharacters());
            //session.persist(acc);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return acc;
    }

    public Account createUserAccount(User deattachedUser, int worldId){
        Session session = DatabaseManager.getSession();
        Transaction tx = null;
        Account account = null;
        try{
            tx = session.beginTransaction();
            User user = session.merge(deattachedUser);
            // Confirm that there's no account
            Query<Account> query = session.createQuery(
                "From Account where user = :user and worldId = :worldId",
                Account.class
            );

            query.setParameter("user", user);
            query.setParameter("worldId", worldId);

            if (query.uniqueResultOptional().isPresent()){
                user.addAccount(query.uniqueResult());
                return query.uniqueResult();
            }

            account = new Account(user, worldId);
            user.addAccount(account);
            account.setUser(user);
            session.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()){
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }

        return account;
    }

    public List<Char> getSelectCharacterFromAccount(Account account) {
        Session session = DatabaseManager.getSession();
        List<Char> charaList = new ArrayList<>();
        try {
            Query<Char> query = session.createQuery(
                "FROM Char WHERE account = :acc",
                Char.class
            );

            query.setParameter(
                "acc",
                account
            );

            // Initialize all characters
            charaList = query.getResultList().stream().peek(
                (chara) -> Hibernate.initialize(chara.getAvatarData())
            ).toList();

        } catch (Exception e){
            throw new RuntimeException();
        } finally {
            DatabaseManager.closeSession(session);
        }
        return charaList;
    }

    public List<Char> getCharactersByAccount(Account account) {
        Session session = DatabaseManager.getSession();
        List<Char> characters = new ArrayList<>();
        try {
            Query<Char> query = session.createQuery(
                "FROM Char where account = :account",
                Char.class
            );

            query.setParameter("account", account);
            characters = new ArrayList<>(query.getResultList());

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return characters;
    }

    public Char createCharacter(
        Account deattachedAccount,
        String name,
        int job,
        int curSelectedRace,
        short curSelectedSubJob,
        byte gender,
        byte skin,
        int face,
        int hair,
        int[] items
    ) {
        Session session = null;//DatabaseManager.getSession();
        Transaction tx = null;
        Char chr = null;
        try {
            session = DatabaseManager.getSession();
            tx = session.beginTransaction();
            Account account = session.get(Account.class, deattachedAccount.getId());

            chr = new Char(
                account,
                name,
                0,
                0,
                job,
                curSelectedSubJob,
                gender,
                skin,
                face,
                hair,
                items
            );

            // Add the default stuff
            JobManager.getJobById((short) job, chr).setCharCreationStats(chr);

            chr.initFuncKeyMaps(0, false);

            CharacterStat cs = chr.getAvatarData().getCharacterStat();
            if (curSelectedRace == JobConstants.LoginJob.DUAL_BLADE.getJobType()) {
                cs.setSubJob(1);
            }
            cs.setCharacterIdForLog(chr.getId());
            cs.setWorldIdForLog(account.getWorldId());
            for (int i : chr.getAvatarData().getAvatarLook().getHairEquips()) {
                Equip equip = ItemData.getEquipDeepCopyFromID(i, false);
                if (equip != null && equip.getItemId() >= 1000000) {
                    equip.setBagIndex(ItemConstants.getBodyPartFromItem(
                        equip.getItemId(), chr.getAvatarData().getAvatarLook().getGender()));
                    chr.addItemToInventory(EQUIPPED, equip, true);
                }
            }
            Equip codex = ItemData.getEquipDeepCopyFromID(1172000, false);
            codex.setInvType(EQUIPPED);
            codex.setBagIndex(BodyPart.MonsterBook.getVal());
            chr.addItemToInventory(EQUIPPED, codex, true);

            chr.setAccount(account);
            account.addCharacter(chr);
            session.persist(chr);

            Hibernate.initialize(chr.getAvatarData());
            Hibernate.initialize(chr.getUser());
            Hibernate.initialize(chr.getAccount());
            Hibernate.initialize(chr.getAvatarData().getAvatarLook());
            Hibernate.initialize(chr.getAvatarData().getCharacterStat());

            tx.commit();

        } catch (Exception e) {
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }

        return chr;
    }

    // Mostly used to login :)
    public Char getCharacterByIdAndAccount(Account account, int charId) {
        Session session = DatabaseManager.getSession();
        Char foundChar = null;
        try {
            Query<Char> query = session.createQuery(
              "FROM Char where account = :account and id = :id",
                Char.class
            );

            query.setParameter("account", account);
            query.setParameter("id", charId);

            foundChar = query.getSingleResultOrNull();
            Hibernate.initialize(foundChar.getAvatarData());
            Hibernate.initialize(foundChar.getAccount());
            Hibernate.initialize(foundChar.getUser());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return foundChar;
    }

    public Char getCharacterById(int charId) {
        Session session = DatabaseManager.getSession();
        Char foundChar = null;
        try {
            Query<Char> query = session.createQuery(
                "FROM Char where id = :id",
                Char.class
            );

            query.setParameter("id", charId);

            foundChar = query.getSingleResultOrNull();
            Hibernate.initialize(foundChar.getAvatarData());
            Hibernate.initialize(foundChar.getAccount());
            Hibernate.initialize(foundChar.getUser());
            Hibernate.initialize(foundChar.getField());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return foundChar;
    }

    public Char getCharacterByName(String name) {
        Session session = DatabaseManager.getSession();
        Char foundChar = null;
        try {
            Query<Char> query = session.createQuery(
                "FROM CharacterStat where name = :name",
                Char.class
            );

            query.setParameter(
                "name",
                name
            );

            foundChar = query.getSingleResultOrNull();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return foundChar;
    }

    public Account getAccountById(int id) {
        Session session = DatabaseManager.getSession();
        Account foundAccount = null;
        try {
            foundAccount = session.find(
                Account.class,
                id
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return foundAccount;
    }

    public Char getCharacterByAvatarData(AvatarData av) {
        Session session = DatabaseManager.getSession();
        Char foundCharacter = null;
        try {
            Query<Char> query = session.createQuery(
              "FROM Char where avatarData = :av",
                Char.class
            );

            query.setParameter(
                "av",
                av.getId()
            );

            foundCharacter = query.getSingleResultOrNull();
            session.persist(foundCharacter);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return foundCharacter;
    }

    public AvatarData getAvatarDataByCharacter(Char chr) {
        Session session = null;
        AvatarData av = null;
        try {
            session = DatabaseManager.getSession();
            session.merge(chr);
            av = chr.getAvatarData();

            if (av != null)
                session.persist(av);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.closeSession(session);
        }
        return av;
    }

}
