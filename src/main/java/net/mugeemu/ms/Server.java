package net.mugeemu.ms;

import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.connection.crypto.MapleCrypto;
import net.mugeemu.ms.connection.db.DatabaseManager;
import net.mugeemu.ms.connection.netty.ChannelAcceptor;
import net.mugeemu.ms.connection.netty.ChannelHandler;
import net.mugeemu.ms.connection.netty.ChatAcceptor;
import net.mugeemu.ms.connection.netty.LoginAcceptor;
import net.mugeemu.ms.constants.GameConstants;
import net.mugeemu.ms.loaders.*;
import net.mugeemu.ms.scripts.ScriptManagerImpl;
import net.mugeemu.ms.util.Loader;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.util.container.Tuple;
import net.mugeemu.ms.world.Channel;
import net.mugeemu.ms.world.World;
import net.mugeemu.ms.world.shop.cashshop.CashShop;
import net.mugeemu.ms.world.shop.cashshop.CashShopCategory;
import net.mugeemu.ms.world.shop.cashshop.CashShopItem;
import net.mugeemu.ms.client.User;
import net.mugeemu.ms.loaders.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created on 2/18/2017.
 */
public class Server extends Properties {

	final Logger log = LogManager.getRootLogger();

	private static final Server server = new Server();

	private List<World> worldList = new ArrayList<>();
	private Set<Integer> users = new HashSet<>(); // just save the ids, no need to save the references
	private CashShop cashShop;

	public static Server getInstance() {
		return server;
	}

	public List<World> getWorlds() {
		return worldList;
	}

	public World getWorldById(int id) {
		return Util.findWithPred(getWorlds(), w -> w.getWorldId() == id);
	}

	private void init(String[] args) {
		log.info("Starting server.");
		long startNow = System.currentTimeMillis();
		DatabaseManager.init();
		log.info("Loaded Hibernate in " + (System.currentTimeMillis() - startNow) + "ms");

		try {
			checkAndCreateDat();
			loadWzData();
            StringData.load();
            FieldData.loadWorldMapFromWz();
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
		ChannelHandler.initHandlers(false);

		//FieldData.loadNPCFromSQL();
		//log.info("Finished loading custom NPCs in " + (System.currentTimeMillis() - startNow) + "ms");

		MapleCrypto.initialize(ServerConstants.VERSION);
		new Thread(new LoginAcceptor()).start();
		new Thread(new ChatAcceptor()).start();
		worldList.add(new World(ServerConfig.WORLD_ID, ServerConfig.SERVER_NAME, GameConstants.CHANNELS_PER_WORLD, ServerConfig.EVENT_MSG));
		long startCashShop = System.currentTimeMillis();
		initCashShop();
		log.info("Loaded Cash Shop in " + (System.currentTimeMillis() - startCashShop) + "ms");

		MonsterCollectionData.loadFromSQL();

		for (World world : getWorlds()) {
			for (Channel channel : world.getChannels()) {
				ChannelAcceptor ca = new ChannelAcceptor();
				ca.channel = channel;
				new Thread(ca).start();
			}
		}
		log.info(String.format("Finished loading server in %dms", System.currentTimeMillis() - startNow));
		new Thread(() -> {
			// inits the script engine
			log.info(String.format("Starting script engine for %s", ScriptManagerImpl.SCRIPT_ENGINE_NAME));
		}).start();

	}

	private void checkAndCreateDat() {
		File file = new File(ServerConstants.DAT_DIR + "/equips");
		boolean exists = file.exists();
		if (!exists) {
			log.info("Dat files cannot be found (at least not the equip dats). All dats will now be generated. This may take a long while.");
			Util.makeDirIfAbsent(ServerConstants.DAT_DIR);
			for (Class c : DataClasses.datCreators) {
				try {
					Method m = c.getMethod("generateDatFiles");
					m.invoke(null);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					System.out.println("ERROR ON: " + c.getName());
					e.printStackTrace();
				}
			}
		}
	}

	public void loadWzData() throws InvocationTargetException, IllegalAccessException {
		for (Class c : DataClasses.dataClasses) {
			for (Method method : c.getMethods()) {
				Loader annotation = method.getAnnotation(Loader.class);
				if (annotation != null) {
					long start = System.currentTimeMillis();
                    method.invoke(c);
                    long total = System.currentTimeMillis() - start;
					log.info(String.format("Took %dms to load using %s", total, method.getName()));
				}
			}
		}
	}

	public static void main(String[] args) {
		getInstance().init(args);
	}

	public Session getNewDatabaseSession() {
		cleanSessions();
		return DatabaseManager.getSession();
	}

	public Tuple<Byte, Client> getChannelFromTransfer(int charId, int worldId) {
		World world = getWorldById(worldId);
		if(world == null) {
			// If it's null, we will search directly on the character data
			Char chara = DatabaseManager.getObjFromDB(Char.class, charId);
			world = getWorldById(chara.getAccount().getWorldId());
		}
		for (Channel c : world.getChannels()) {
			if (c.getTransfers().containsKey(charId)) {
				return c.getTransfers().get(charId);
			}
		}
		return null;
	}

	public void cleanSessions() {
		DatabaseManager.cleanUpSessions();
	}

	public void clearCache() {
		ChannelHandler.initHandlers(true);
		DropData.clear();
		FieldData.clear();
		ItemData.clear();
		MobData.clear();
		NpcData.clear();
		QuestData.clear();
		SkillData.clear();
		ReactorData.clear();
		for (World world : getWorlds()) {
			world.clearCache();
		}
	}

	public void initCashShop() {
		cashShop = new CashShop();
		try(Session session = DatabaseManager.getSession()) {
			Transaction transaction = session.beginTransaction();

			Query query = session.createQuery("FROM CashShopCategory");
			List<CashShopCategory> categories = query.list();
			categories.sort(Comparator.comparingInt(CashShopCategory::getIdx));
			cashShop.setCategories(new ArrayList<>(categories));

			query = session.createQuery("FROM CashShopItem");
			List<CashShopItem> items = query.list();
			items.forEach(item -> cashShop.addItem(item));

			transaction.commit();
		}

	}

	public CashShop getCashShop() {
		return this.cashShop;
	}

	public void addUser(User user) {
		users.add(user.getId());
	}

	public void removeUser(User user) {
		users.remove(user.getId());
	}

	public boolean isUserLoggedIn(User user) {
		return users.contains(user.getId());
	}
}
