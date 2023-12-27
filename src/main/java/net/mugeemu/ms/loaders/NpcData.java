package net.mugeemu.ms.loaders;

import net.mugeemu.ms.connection.db.DatabaseManager;
import net.mugeemu.ms.life.npc.Npc;
import net.mugeemu.ms.world.shop.NpcShopDlg;
import net.mugeemu.ms.world.shop.NpcShopItem;
import net.mugeemu.ms.ServerConstants;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.util.XMLApi;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.*;

/**
 * Created on 2/19/2018.
 */
public class NpcData {
	private static final Logger log = Logger.getLogger(NpcData.class);
	private static final boolean LOG_UNKS = false;

	private static Set<Npc> npcs = new HashSet<>();
	private static Map<Integer, NpcShopDlg> shops = new HashMap<>();

	private static Map<Integer, NpcShopDlg> getShops() {
		return shops;
	}

	private static void addShop(int id, NpcShopDlg nsd) {
		getShops().put(id, nsd);
	}

	private static void loadNpcsFromWz() throws IOException {
		String nxDir = ServerConstants.NX_DIR + "/Npc.nx";
		NXNode npcNX = new LazyNXFile(nxDir).getRoot();

		for (NXNode npcNode : npcNX) {
			Npc npc = new Npc(0);
			int id = Integer.parseInt(npcNode.getName().replace(".img",""));
			npc.setTemplateId(id);
			npc.setMove(npcNode.hasChild("move"));
			NXNode scriptNode = npcNode.getChild("script");
			if (scriptNode != null) {
				for (NXNode idNode : scriptNode) {
					String scriptIDString = idNode.getName();
					if (!Util.isNumber(scriptIDString)) {
						continue;
					}
					int scriptID = Integer.parseInt(scriptIDString);
					NXNode scriptValueNode = idNode.getChild("script");
					if (scriptValueNode != null) {
						String scriptName = scriptValueNode.get().toString();
						npc.getScripts().put(scriptID, scriptName);
					}
				}
			}
			NXNode infoNode = npcNode.getChild("info");
			for (NXNode infoChildNode : infoNode) {
				String name = infoChildNode.getName();
				switch (name) {
					case "trunkGet":
						String value = infoChildNode.get().toString();
						npc.setTrunkGet(Integer.parseInt(
							infoChildNode.get().toString()
						));
						break;
					case "trunkPut":
						npc.setTrunkPut(Integer.parseInt(
							infoChildNode.get().toString()
						));
						break;
				}
			}
			getBaseNpcs().add(npc);
		}
	}

	public static void saveNpcsToDat(String dir) {
		Util.makeDirIfAbsent(dir);
		for (Npc npc : getBaseNpcs()) {
			File file = new File(String.format("%s/%d.dat", dir, npc.getTemplateId()));
			try(DataOutputStream das = new DataOutputStream(new FileOutputStream(file))) {
				das.writeInt(npc.getTemplateId());
				das.writeBoolean(npc.isMove());
				das.writeInt(npc.getTrunkGet());
				das.writeInt(npc.getTrunkPut());
				das.writeShort(npc.getScripts().size());
				npc.getScripts().forEach((key, val) -> {
					try {
						das.writeInt(key);
						das.writeUTF(val);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static Npc getNpc(int id) {
		return getBaseNpcs().stream().filter(npc -> npc.getTemplateId() == id).findFirst().orElse(null);
	}

	public static Npc getNpcDeepCopyById(int id) {
		Npc res = getNpc(id);
		if(res != null) {
			res = res.deepCopy();
		} else {
			File file = new File(String.format("%s/npc/%d.dat", ServerConstants.DAT_DIR, id));
			if (file.exists()) {
				res = loadNpcFromDat(file).deepCopy();
				getBaseNpcs().add(res);
			}
		}
		return res;
	}

	private static Npc loadNpcFromDat(File file) {
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
			Npc npc = new Npc(dis.readInt());
			npc.setMove(dis.readBoolean());
			npc.setTrunkGet(dis.readInt());
			npc.setTrunkPut(dis.readInt());
			short size = dis.readShort();
			for (int i = 0; i < size; i++) {
				int id = dis.readInt();
				String val = dis.readUTF();
				npc.getScripts().put(id, val);
			}
			getBaseNpcs().add(npc);
			return npc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static NpcShopDlg loadNpcShopDlgFromDB(int id) {
		List<NpcShopItem> items = DatabaseManager.getObjListFromDB(NpcShopItem.class, "shopid", id);
		if (items == null || items.size() == 0) {
			return null;
		}
		NpcShopDlg nsd = new NpcShopDlg();
		nsd.setNpcTemplateID(id);
		nsd.setShopID(id);
		items.sort(Comparator.comparingInt(NpcShopItem::getItemID));
		nsd.setItems(items);
		addShop(id, nsd);
		return nsd;
	}

	public static NpcShopDlg getShopById(int id) {
		return getShops().getOrDefault(id, loadNpcShopDlgFromDB(id));
	}

	public static void generateDatFiles() {
		log.info("Started generating npc data.");
		long start = System.currentTimeMillis();
		try {
			loadNpcsFromWz();
		} catch (IOException e){
            throw new RuntimeException(e);
		}
		log.info(String.format("Completed generating npc data in %dms.", System.currentTimeMillis() - start));
	}

	public static Set<Npc> getBaseNpcs() {
		return npcs;
	}

	public static void main(String[] args) {
		generateDatFiles();
	}

	public static void clear() {
		getBaseNpcs().clear();
		getShops().clear();
	}
}
