package net.mugeemu.ms.connection.db;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import net.mugeemu.ms.client.Account;
import net.mugeemu.ms.client.LinkSkill;
import net.mugeemu.ms.client.User;
import net.mugeemu.ms.client.alliance.Alliance;
import net.mugeemu.ms.client.anticheat.Offense;
import net.mugeemu.ms.client.anticheat.OffenseManager;
import net.mugeemu.ms.client.character.*;
import net.mugeemu.ms.client.character.quest.progress.*;
import net.mugeemu.ms.client.guild.Guild;
import net.mugeemu.ms.client.guild.GuildMember;
import net.mugeemu.ms.client.guild.GuildRequestor;
import net.mugeemu.ms.client.guild.GuildSkill;
import net.mugeemu.ms.client.character.avatar.AvatarData;
import net.mugeemu.ms.client.character.avatar.AvatarLook;
import net.mugeemu.ms.client.character.cards.CharacterCard;
import net.mugeemu.ms.client.character.cards.MonsterBookInfo;
import net.mugeemu.ms.client.character.damage.DamageSkinSaveData;
import net.mugeemu.ms.client.character.items.Equip;
import net.mugeemu.ms.client.character.items.Inventory;
import net.mugeemu.ms.client.character.items.Item;
import net.mugeemu.ms.client.character.items.PetItem;
import net.mugeemu.ms.client.character.keys.FuncKeyMap;
import net.mugeemu.ms.client.character.keys.Keymapping;
import net.mugeemu.ms.client.character.potential.CharacterPotential;
import net.mugeemu.ms.client.character.quest.Quest;
import net.mugeemu.ms.client.character.quest.QuestManager;
import net.mugeemu.ms.client.character.skills.ChosenSkill;
import net.mugeemu.ms.client.character.skills.Skill;
import net.mugeemu.ms.client.character.skills.StolenSkill;
import net.mugeemu.ms.client.friend.Friend;
import net.mugeemu.ms.client.guild.bbs.BBSRecord;
import net.mugeemu.ms.client.guild.bbs.BBSReply;
import net.mugeemu.ms.client.trunk.Trunk;
import net.mugeemu.ms.handlers.EventManager;
import net.mugeemu.ms.life.Familiar;
import net.mugeemu.ms.life.Merchant.EmployeeTrunk;
import net.mugeemu.ms.life.Merchant.MerchantItem;
import net.mugeemu.ms.life.drop.DropInfo;
import net.mugeemu.ms.loaders.containerclasses.EquipDrop;
import net.mugeemu.ms.loaders.containerclasses.MonsterCollectionGroupRewardInfo;
import net.mugeemu.ms.loaders.containerclasses.MonsterCollectionMobInfo;
import net.mugeemu.ms.loaders.containerclasses.MonsterCollectionSessionRewardInfo;
import net.mugeemu.ms.world.shop.NpcShopItem;
import net.mugeemu.ms.world.shop.cashshop.CashItemInfo;
import net.mugeemu.ms.world.shop.cashshop.CashShopCategory;
import net.mugeemu.ms.world.shop.cashshop.CashShopItem;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import net.mugeemu.ms.util.FileTime;
import net.mugeemu.ms.util.SystemTime;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 12/12/2017.
 */
public class DatabaseManager {
    private static final Logger log = Logger.getLogger(DatabaseManager.class);
    private static final int KEEP_ALIVE_MS = 10 * 60 * 1000; // 10 minutes

    private static SessionFactory sessionFactory;
   // private static List<Session> sessions;
    private static final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Map<Session, Session>> threadLocalSessions = new ThreadLocal<>();




    public static void init() {
        Configuration configuration = new Configuration().configure();
        configuration.setProperty("autoReconnect", "true");
        Class[] dbClasses = new Class[] {
                User.class,
                FileTime.class,
                SystemTime.class,
                NonCombatStatDayLimit.class,
                CharacterCard.class,
                Item.class,
                Equip.class,
                Inventory.class,
                Skill.class,
                FuncKeyMap.class,
                Keymapping.class,
                SPSet.class,
                ExtendSP.class,
                CharacterStat.class,
                AvatarLook.class,
                AvatarData.class,
                Char.class,
                Account.class,
                QuestManager.class,
                Quest.class,
                QuestProgressRequirement.class,
                QuestProgressLevelRequirement.class,
                QuestProgressItemRequirement.class,
                QuestProgressMobRequirement.class,
                QuestProgressMoneyRequirement.class,
                Guild.class,
                GuildMember.class,
                GuildRequestor.class,
                GuildSkill.class,
                BBSRecord.class,
                BBSReply.class,
                Friend.class,
                Macro.class,
                DamageSkinSaveData.class,
                Trunk.class,
                PetItem.class,
                MonsterBookInfo.class,
                CharacterPotential.class,
                LinkSkill.class,
                Familiar.class,
                StolenSkill.class,
                ChosenSkill.class,
                CashItemInfo.class,
                CashShopItem.class,
                CashShopCategory.class,
                MonsterCollectionSessionRewardInfo.class,
                MonsterCollectionGroupRewardInfo.class,
                MonsterCollectionMobInfo.class,
                MonsterCollection.class,
                MonsterCollectionReward.class,
                MonsterCollectionExploration.class,
                Alliance.class,
                DropInfo.class,
                Offense.class,
                OffenseManager.class,
                NpcShopItem.class,
                EquipDrop.class,
                EmployeeTrunk.class,
                MerchantItem.class,
        };
        for(Class clazz : dbClasses) {
            configuration.addAnnotatedClass(clazz);
        }
        sessionFactory = configuration.buildSessionFactory();
        sendHeartBeat();
    }

    /**
     * Sends a simple query to the DB to ensure that the connection stays alive.
     */
    private static void sendHeartBeat() {
        Session session = getSession();
        Transaction t = session.beginTransaction();
        Query<Char> q = session.createQuery("from Char where id = 1", Char.class);
        q.list();
        t.commit();
        session.close();
        EventManager.addEvent(DatabaseManager::sendHeartBeat, KEEP_ALIVE_MS);
    }

    /*public static Session getSession() {
        Session session = sessionFactory.openSession();
        sessions.add(session);
        return session;
    }*/
    public static Session getSession() {
        Map<Session, Session> sessionsMap = threadLocalSessions.get();
        if (sessionsMap == null) {
            sessionsMap = new HashMap<>();
            threadLocalSessions.set(sessionsMap);
        }
        Session session = sessionFactory.openSession();
        sessionsMap.put(session, session);
        return session;
    }

    public static void closeSession(Session session) {
        Map<Session, Session> sessionsMap = threadLocalSessions.get();
        if (sessionsMap != null && sessionsMap.containsKey(session)) {
            sessionsMap.remove(session);
            if (session.isOpen()) {
                session.close();
            }
            // Clean up the threadLocal map if there's no more sessions in it
            if (sessionsMap.isEmpty()) {
                threadLocalSessions.remove();
            }
        }
    }

    public static void cleanUpSessions() {
        threadLocalSessions.get().clear();
        threadLocalSessions.remove();
    }

    public static <T> void saveOrUpdate(T obj, boolean save) {
        log.info(String.format("%s: Trying to save obj %s.", LocalDateTime.now(), obj));
        Session session = null;
        Transaction t = null;
        try {
            session = getSession();
            t = session.beginTransaction();

            if(save) {
                session.persist(obj);
            } else {
                session.merge(obj);
            }

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            log.error("Error saving object to DB", e);
        } finally {
            cleanUpSessions();
        }
    }

    public static <T> void merge(T obj) {
        Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(obj);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close(); // Closing the session after use to avoid leaks
            sessionThreadLocal.remove(); // Cleanup ThreadLocal to prevent memory leaks
        }
    }

    public static <T> void persist(T obj) {
        Session session = getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(obj);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close(); // Closing the session after use to avoid leaks
            sessionThreadLocal.remove(); // Cleanup ThreadLocal to prevent memory leaks
        }
    }

    public static <T> void saveToDB(T obj) {
        log.info(String.format("%s: Trying to save obj %s.", LocalDateTime.now(), obj));
        synchronized (obj) {
            try (Session session = getSession()) {
                Transaction t = session.beginTransaction();
                session.saveOrUpdate(obj.getClass().getName(), obj);
                t.commit();
            }
        }
        cleanUpSessions();
    }

    public static <T> void deleteFromDB(T obj) {
        log.info(String.format("%s: Trying to delete obj %s.", LocalDateTime.now(), obj));
        Session session = null;
        Transaction t = null;
        synchronized (obj) {
            try {
                session = getSession();
                t = session.beginTransaction();
                session.remove(obj);
                t.commit();
            } catch (Exception e) {
                t.rollback();
            } finally {
                cleanUpSessions();
            }
        }
    }

    public static <T> T getObjFromDB(Class<T> clazz, int id) {
        log.info(String.format("%s: Trying to get obj %s with id %d.", LocalDateTime.now(), clazz, id));
        T o = null;
        try(Session session = getSession()) {
            o = session.get(clazz, id);
        }
        return o;
    }

    public static <T> T getObjFromDB(Class<T> clazz, String name) {
        return getObjFromDB(clazz, "name", name);
    }

    public static <T> T getObjFromDB(Class<T> clazz, String columnName, Object value) {
        log.info(String.format("%s: Trying to get obj %s with value %s.", LocalDateTime.now(), clazz, value));
        T o = null;
        try (Session session = getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            Predicate predicate = cb.equal(root.get(columnName), value) ;
            cq.select(root).where(predicate);
            o = session.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return o;
    }

    public static <T> List<T> getObjListFromDB(Class<T> clazz) {
        List<T> o = null;
        try (Session session = getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.select(root).where();
            o = new ArrayList<>(session.createQuery(cq).getResultList());
        } catch (Exception e) {
            return null;
        }
        return o;
    }

    public static <T> List<T> getObjListFromDB(Class<T> clazz, String columnName, Object value) {
        List<T> o = null;
        try (Session session = getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            Predicate predicate = cb.equal(root.get(columnName), value) ;
            cq.select(root).where(predicate);
            Query<T> query = session.createQuery(cq);
            o = new ArrayList<>(query.getResultList());
        } catch (Exception e) {
            return null;
        }
        return o;
    }
}
