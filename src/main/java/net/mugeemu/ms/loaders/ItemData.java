package net.mugeemu.ms.loaders;

import net.mugeemu.ms.client.character.items.*;
import net.mugeemu.ms.constants.GameConstants;
import net.mugeemu.ms.constants.ItemConstants;
import net.mugeemu.ms.enums.*;
import net.mugeemu.ms.loaders.containerclasses.ItemInfo;
import net.mugeemu.ms.loaders.containerclasses.ItemRewardInfo;
import net.mugeemu.ms.loaders.containerclasses.PetInfo;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.util.*;
import org.apache.log4j.LogManager;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static net.mugeemu.ms.client.character.items.Item.Type.ITEM;
import static net.mugeemu.ms.enums.ScrollStat.*;

/**
 * Created on 11/17/2017.
 */
public class ItemData {
    public static Map<Integer, Equip> equips = new HashMap<>();
    public static Map<Integer, ItemInfo> items = new HashMap<>();
    public static Map<Integer, PetInfo> pets = new HashMap<>();
    public static Map<Integer, ItemOption> itemOptions = new HashMap<>();
    public static List<ItemOption> filteredItemOptions = new ArrayList<>();
    public static Map<Integer, Integer> skillIdByItemId = new HashMap<>();
    private static Set<Integer> startingItems = new HashSet<>();
    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();
    private static final boolean LOG_UNKS = false;


    /**
     * Creates a new Equip given an itemId.
     *
     * @param itemId         The itemId of the wanted equip.
     * @param randomizeStats whether or not to randomize the stats of the created object
     * @return A deep copy of the default values of the corresponding Equip, or null if there is no equip with itemId
     * <code>itemId</code>.
     */
    public static Equip getEquipDeepCopyFromID(int itemId, boolean randomizeStats) {
        Equip e = getEquipById(itemId);
        Equip ret = e == null ? null : e.deepCopy();
        if (ret != null) {
            ret.setQuantity(1);
            ret.setCuttable((short) -1);
            ret.setHyperUpgrade((short) ItemState.AmazingHyperUpgradeChecked.getVal());
            ret.setType(Item.Type.EQUIP);
            ret.setInvType(InvType.EQUIP);
            if (randomizeStats) {
                if (ItemConstants.canEquipHaveFlame(ret)) {
                    ret.randomizeFlameStats(true);
                }
                if (ItemConstants.canEquipHavePotential(ret)) {
                    ItemGrade grade = ItemGrade.None;
                    if (Util.succeedProp(GameConstants.RANDOM_EQUIP_UNIQUE_CHANCE)) {
                        grade = ItemGrade.HiddenUnique;
                    } else if (Util.succeedProp(GameConstants.RANDOM_EQUIP_EPIC_CHANCE)) {
                        grade = ItemGrade.HiddenEpic;
                    } else if (Util.succeedProp(GameConstants.RANDOM_EQUIP_RARE_CHANCE)) {
                        grade = ItemGrade.HiddenRare;
                    }
                    if (grade != ItemGrade.None) {
                        ret.setHiddenOptionBase(grade.getVal(), ItemConstants.THIRD_LINE_CHANCE);
                    }
                }
            }
        }
        return ret;
    }

    public static Equip getEquipById(int itemId) {
        return getEquips().getOrDefault(itemId, getEquipFromFile(itemId));
    }

    private static Equip getEquipFromFile(int itemId) {
        try {
            return readEquipFromNX(itemId);
        } catch (IOException e) {
            return null;
            //throw new RuntimeException(e);
        }
    }

    private static Equip readEquipFromNX(int itemId) throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Character.nx";
        NXNode itemNode = new LazyNXFile(nxDir)
            .getRoot()
            .getChild(itemId + ".img");

        if (itemNode != null) {
            return ItemData.nodeToEquip(itemNode);
        }
        return null;
    }


    // Changed to NX files.
    public static void loadEquipsFromWz() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Character.nx";
        NXNode nxChara = new LazyNXFile(nxDir).getRoot();
        String[] subMaps = new String[]{"Accessory", "Android", "Cap", "Cape", "Coat", "Dragon", "Face", "Glove",
                "Longcoat", "Mechanic", "Pants", "PetEquip", "Ring", "Shield", "Shoes", "Totem", "Weapon", "MonsterBook"};
        for (String category : subMaps) {
            NXNode nodes = nxChara.getChild(category);

            if (nodes == null || nodes.getChildCount() <= 0) {
                continue;
            } else if (nodes.hasChild(category)) {
                nodes = nodes.getChild(category);
            }

            for (NXNode item : nodes) {
                Equip equip = ItemData.nodeToEquip(item);

                if (equip != null) {
                    equips.put(equip.getItemId(), equip);
                }
            }
        }
    }

    public static ItemInfo loadItemByFile(File file) {
        ItemInfo itemInfo = null;
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            itemInfo = new ItemInfo();
            itemInfo.setItemId(dataInputStream.readInt());
            itemInfo.setInvType(InvType.getInvTypeByString(dataInputStream.readUTF()));
            itemInfo.setCash(dataInputStream.readBoolean());
            itemInfo.setPrice(dataInputStream.readInt());
            itemInfo.setSlotMax(dataInputStream.readInt());
            itemInfo.setTradeBlock(dataInputStream.readBoolean());
            itemInfo.setNotSale(dataInputStream.readBoolean());
            itemInfo.setPath(dataInputStream.readUTF());
            itemInfo.setNoCursed(dataInputStream.readBoolean());
            itemInfo.setBagType(dataInputStream.readInt());
            itemInfo.setCharmEXP(dataInputStream.readInt());
            itemInfo.setSenseEXP(dataInputStream.readInt());
            itemInfo.setQuest(dataInputStream.readBoolean());
            itemInfo.setReqQuestOnProgress(dataInputStream.readInt());
            itemInfo.setNotConsume(dataInputStream.readBoolean());
            itemInfo.setMonsterBook(dataInputStream.readBoolean());
            itemInfo.setMobID(dataInputStream.readInt());
            itemInfo.setCreateID(dataInputStream.readInt());
            itemInfo.setMobHP(dataInputStream.readInt());
            itemInfo.setNpcID(dataInputStream.readInt());
            itemInfo.setLinkedID(dataInputStream.readInt());
            itemInfo.setScript(dataInputStream.readUTF());
            itemInfo.setScriptNPC(dataInputStream.readInt());
            short size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                ScrollStat ss = ScrollStat.getScrollStatByString(dataInputStream.readUTF());
                int val = dataInputStream.readInt();
                itemInfo.putScrollStat(ss, val);
            }
            size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                SpecStat ss = SpecStat.getSpecStatByName(dataInputStream.readUTF());
                int val = dataInputStream.readInt();
                itemInfo.putSpecStat(ss, val);
            }
            size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                itemInfo.addQuest(dataInputStream.readInt());
            }

            size = dataInputStream.readShort();
            for(int i = 0; i < size; i++) {
                itemInfo.getReqItemIds().add(dataInputStream.readInt());
            }

            size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                itemInfo.addSkill(dataInputStream.readInt());
            }

            size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                ItemRewardInfo iri = new ItemRewardInfo();
                iri.setCount(dataInputStream.readInt());
                iri.setItemID(dataInputStream.readInt());
                iri.setProb(dataInputStream.readDouble());
                iri.setPeriod(dataInputStream.readInt());
                iri.setEffect(dataInputStream.readUTF());
                itemInfo.addItemReward(iri);
            }
            itemInfo.setReqSkillLv(dataInputStream.readInt());
            itemInfo.setMasterLv(dataInputStream.readInt());

            itemInfo.setMoveTo(dataInputStream.readInt());
            itemInfo.setSkillId(dataInputStream.readInt());
            getItems().put(itemInfo.getItemId(), itemInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemInfo;

    }
    public static PetInfo getPetInfoByID(int id) {
        return getPets().getOrDefault(id, loadPetByID(id));
    }

    public static PetInfo loadPetByID(int id) {
        return getPets().getOrDefault(id, null);
    }

    public static void loadPetsFromWZ() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Item.nx";
        NXNode petsNode = new LazyNXFile(nxDir).getRoot().getChild("Pet");
        for (NXNode pet : petsNode) {
            int id = Integer.parseInt(pet.getName().replace(".img", ""));
            PetInfo pi = new PetInfo();
            pi.setItemID(id);
            pi.setInvType(InvType.CONSUME);
            for (NXNode node : pet.getChild("info")) {
                String name = node.getName();
                String value = "";
                if(node.get() != null)
                    value = node.get().toString();

                switch (name) {
                    case "icon":
                    case "iconD":
                    case "iconRaw":
                    case "iconRawD":
                    case "hungry":
                    case "nameTag":
                    case "chatBalloon":
                    case "noHungry":
                        break;
                    case "life":
                        pi.setLife(Integer.parseInt(value));
                        break;
                    case "setItemID":
                        pi.setSetItemID(Integer.parseInt(value));
                        break;
                    case "evolutionID":
                        pi.setEvolutionID(Integer.parseInt(value));
                        break;
                    case "type":
                        pi.setType(Integer.parseInt(value));
                        break;
                    case "limitedLife":
                        pi.setLimitedLife(Integer.parseInt(value));
                        break;
                    case "evol1":
                        pi.setEvol1(Integer.parseInt(value));
                        break;
                    case "evol2":
                        pi.setEvol2(Integer.parseInt(value));
                        break;
                    case "evol3":
                        pi.setEvol3(Integer.parseInt(value));
                        break;
                    case "evol4":
                        pi.setEvol4(Integer.parseInt(value));
                        break;
                    case "evol5":
                        pi.setEvol5(Integer.parseInt(value));
                        break;
                    case "evolProb1":
                        pi.setProbEvol1(Integer.parseInt(value));
                        break;
                    case "evolProb2":
                        pi.setProbEvol2(Integer.parseInt(value));
                        break;
                    case "evolProb3":
                        pi.setProbEvol3(Integer.parseInt(value));
                        break;
                    case "evolProb4":
                        pi.setProbEvol4(Integer.parseInt(value));
                        break;
                    case "evolProb5":
                        pi.setProbEvol5(Integer.parseInt(value));
                        break;
                    case "evolReqItemID":
                        pi.setEvolReqItemID(Integer.parseInt(value));
                        break;
                    case "evolReqPetLvl":
                        pi.setEvolReqPetLvl(Integer.parseInt(value));
                        break;
                    case "evolNo":
                        pi.setEvolNo(Integer.parseInt(value));
                        break;
                    case "permanent":
                        pi.setPermanent(Integer.parseInt(value) != 0);
                        break;
                    case "pickupItem":
                        pi.setPickupItem(Integer.parseInt(value) != 0);
                        break;
                    case "interactByUserAction":
                        pi.setInteractByUserAction(Integer.parseInt(value) != 0);
                        break;
                    case "longRange":
                        pi.setLongRange(Integer.parseInt(value) != 0);
                        break;
                    case "giantPet":
                        pi.setGiantPet(Integer.parseInt(value) != 0);
                        break;
                    case "noMoveToLocker":
                        pi.setAllowOverlappedSet(Integer.parseInt(value) != 0);
                        break;
                    case "allowOverlappedSet":
                        pi.setAllowOverlappedSet(Integer.parseInt(value) != 0);
                        break;
                    case "noRevive":
                        pi.setNoRevive(Integer.parseInt(value) != 0);
                        break;
                    case "noScroll":
                        pi.setNoScroll(Integer.parseInt(value) != 0);
                        break;
                    case "autoBuff":
                        pi.setAutoBuff(Integer.parseInt(value) != 0);
                        break;
                    case "multiPet":
                        pi.setAutoBuff(Integer.parseInt(value) != 0);
                        break;
                    case "autoReact":
                        pi.setAutoReact(Integer.parseInt(value) != 0);
                        break;
                    case "pickupAll":
                        pi.setPickupAll(Integer.parseInt(value) != 0);
                        break;
                    case "sweepForDrop":
                        pi.setSweepForDrop(Integer.parseInt(value) != 0);
                        break;
                    case "consumeMP":
                        pi.setConsumeMP(Integer.parseInt(value) != 0);
                        break;
                    case "evol":
                        pi.setEvol(Integer.parseInt(value) != 0);
                        break;
                    case "starPlanetPet":
                        pi.setStarPlanetPet(Integer.parseInt(value) != 0);
                        break;
                    case "cash":
                        pi.setCash(Integer.parseInt(value) != 0);
                        pi.setInvType(InvType.CASH);
                        pi.setCash(true);
                        break;
                    case "runScript":
                        pi.setRunScript(value);
                        break;
                    default:
                        if (LOG_UNKS) {
                            log.warn(String.format("Unhandled pet node, name = %s, value = %s.", name, value));
                        }
                        break;
                }
            }
            addPetInfo(pi);
            ItemInfo ii = new ItemInfo();
            ii.setItemId(pi.getItemID());
            ii.setInvType(pi.getInvType());
            addItemInfo(ii);
        }
    }

    public static void loadItemsFromWZ() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Item.nx";
        String[] categories = new String[]{"Cash", "Consume", "Etc", "Install", "Special"}; // not pet
        for (String category : categories) {
            NXNode categoryNode = new LazyNXFile(nxDir).getRoot().getChild(category);
            for (NXNode idPrefixNode : categoryNode) {
                for (NXNode itemNode : idPrefixNode) {
                    ItemInfo item = nodeToItem(itemNode, category);
                    if (item != null) items.put(item.getItemId(), item);
                }
            }
        }
    }

    public static void loadMountItemsFromFile() {
        File file = new File(String.format("%s/mountsFromItem.txt", ServerConstants.RESOURCES_DIR));
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(" ");
                int itemId = Integer.parseInt(lineSplit[0]);
                int skillId = Integer.parseInt(lineSplit[1]);
                skillIdByItemId.put(itemId, skillId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getSkillIdByItemId(int itemId) {
        return skillIdByItemId.getOrDefault(itemId, 0);
    }

    public static Item getDeepCopyByItemInfo(ItemInfo itemInfo) {
        if (itemInfo == null) {
            return null;
        }
        Item res = new Item();
        res.setItemId(itemInfo.getItemId());
        res.setQuantity(1);
        res.setType(ITEM);
        res.setInvType(itemInfo.getInvType());
        res.setCash(itemInfo.isCash());
        return res;
    }

    public static Item getItemDeepCopy(int id) {
        return getItemDeepCopy(id, false);
    }

    public static Item getItemDeepCopy(int id, boolean randomize) {
        if (ItemConstants.isEquip(id)) {
            return getEquipDeepCopyFromID(id, randomize);
        } else if (ItemConstants.isPet(id)) {
            return getPetDeepCopyFromID(id);
        }
        return getDeepCopyByItemInfo(getItemInfoByID(id));
    }

    private static PetItem getPetDeepCopyFromID(int id) {
        return getPetInfoByID(id) == null ? null : getPetInfoByID(id).createPetItem();
    }

    public static ItemInfo getItemInfoByID(int itemID) {
        return getItems().getOrDefault(itemID, null);
    }

    public static Map<Integer, Equip> getEquips() {
        return equips;
    }

    @Loader(varName = "itemOptions")
    public static void loadItemOptionsFromWZ() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Item.nx";
        NXNode itemOptions = new LazyNXFile(nxDir).resolve("ItemOption.img");

        for (NXNode option : itemOptions) {
            ItemOption io = new ItemOption();
            String nodeName = option.getName();
            io.setId(Integer.parseInt(nodeName));

            NXNode infoNode = option.getChild("info");
            if (infoNode != null) {
                for (NXNode infoChild : infoNode){
                    String name = infoChild.getName();
                    String value = infoChild.get().toString();

                    switch (name) {
                        case "optionType":
                            io.setOptionType(Integer.parseInt(value));
                            break;
                        case "weight":
                            io.setWeight(Integer.parseInt(value));
                            break;
                        case "reqLevel":
                            io.setReqLevel(Integer.parseInt(value));
                            break;
                        case "string":
                            io.setString(value);
                            break;
                    }
                }
            }

            NXNode levelNode = option.getChild("level");
            if (levelNode != null) {
                for (NXNode levelChild : levelNode) {
                    int level = Integer.parseInt(levelChild.getName());
                    for (NXNode levelInfoNode : levelChild) {
                        String name = levelInfoNode.getName();
                        String stringValue = levelInfoNode.get().toString();
                        int value = 0;
                        if (Util.isNumber(stringValue)) {
                            value = Integer.parseInt(stringValue);
                        }
                        switch (name) {
                            case "incSTR":
                                io.addStatValue(level, BaseStat.str, value);
                                break;
                            case "incDEX":
                                io.addStatValue(level, BaseStat.dex, value);
                                break;
                            case "incINT":
                                io.addStatValue(level, BaseStat.inte, value);
                                break;
                            case "incLUK":
                                io.addStatValue(level, BaseStat.luk, value);
                                break;
                            case "incMHP":
                                io.addStatValue(level, BaseStat.mhp, value);
                                break;
                            case "incMMP":
                                io.addStatValue(level, BaseStat.mmp, value);
                                break;
                            case "incACC":
                                io.addStatValue(level, BaseStat.acc, value);
                                break;
                            case "incEVA":
                                io.addStatValue(level, BaseStat.eva, value);
                                break;
                            case "incSpeed":
                                io.addStatValue(level, BaseStat.speed, value);
                                break;
                            case "incJump":
                                io.addStatValue(level, BaseStat.jump, value);
                                break;
                            case "incPAD":
                                io.addStatValue(level, BaseStat.pad, value);
                                break;
                            case "incMAD":
                                io.addStatValue(level, BaseStat.mad, value);
                                break;
                            case "incPDD":
                                io.addStatValue(level, BaseStat.pdd, value);
                                break;
                            case "incMDD":
                                io.addStatValue(level, BaseStat.mdd, value);
                                break;
                            case "incCr":
                                io.addStatValue(level, BaseStat.cr, value);
                                break;
                            case "incPADr":
                                io.addStatValue(level, BaseStat.padR, value);
                                break;
                            case "incMADr":
                                io.addStatValue(level, BaseStat.madR, value);
                                break;
                            case "incSTRr":
                                io.addStatValue(level, BaseStat.strR, value);
                                break;
                            case "incDEXr":
                                io.addStatValue(level, BaseStat.dexR, value);
                                break;
                            case "incINTr":
                                io.addStatValue(level, BaseStat.intR, value);
                                break;
                            case "incLUKr":
                                io.addStatValue(level, BaseStat.lukR, value);
                                break;
                            case "ignoreTargetDEF":
                                io.addStatValue(level, BaseStat.ied, value);
                                break;
                            case "incDAMr":
                                io.addStatValue(level, BaseStat.fd, value);
                                break;
                            case "boss":
                                NXNode incDamgNode = levelChild.getChild("incDAMr");
                                if (incDamgNode != null) {
                                    value = Integer.parseInt(incDamgNode.get().toString());
                                }
                                io.addStatValue(level, BaseStat.bd, value);
                                break;
                            case "incAllskill":
                                io.addStatValue(level, BaseStat.incAllSkill, value);
                                break;
                            case "incMHPr":
                                io.addStatValue(level, BaseStat.mhpR, value);
                                break;
                            case "incMMPr":
                                io.addStatValue(level, BaseStat.mmpR, value);
                                break;
                            case "incACCr":
                                io.addStatValue(level, BaseStat.accR, value);
                                break;
                            case "incEVAr":
                                io.addStatValue(level, BaseStat.evaR, value);
                                break;
                            case "incPDDr":
                                io.addStatValue(level, BaseStat.pddR, value);
                                break;
                            case "incMDDr":
                                io.addStatValue(level, BaseStat.mddR, value);
                                break;
                            case "RecoveryHP":
                                io.addStatValue(level, BaseStat.hpRecovery, value);
                                break;
                            case "RecoveryMP":
                                io.addStatValue(level, BaseStat.mpRecovery, value);
                                break;
                            case "incMaxDamage":
                                io.addStatValue(level, BaseStat.damageOver, value);
                                break;
                            case "incSTRlv":
                                io.addStatValue(level, BaseStat.strLv, value);
                                break;
                            case "incDEXlv":
                                io.addStatValue(level, BaseStat.dexLv, value);
                                break;
                            case "incINTlv":
                                io.addStatValue(level, BaseStat.intLv, value);
                                break;
                            case "incLUKlv":
                                io.addStatValue(level, BaseStat.lukLv, value);
                                break;
                            case "RecoveryUP":
                                io.addStatValue(level, BaseStat.recoveryUp, value);
                                break;
                            case "incTerR":
                                io.addStatValue(level, BaseStat.ter, value);
                                break;
                            case "incAsrR":
                                io.addStatValue(level, BaseStat.asr, value);
                                break;
                            case "incEXPr":
                                io.addStatValue(level, BaseStat.expR, value);
                                break;
                            case "mpconReduce":
                                io.addStatValue(level, BaseStat.mpconReduce, value);
                                break;
                            case "reduceCooltime":
                                io.addStatValue(level, BaseStat.reduceCooltime, value);
                                break;
                            case "incMesoProp":
                                io.addStatValue(level, BaseStat.mesoR, value);
                                break;
                            case "incRewardProp":
                                io.addStatValue(level, BaseStat.dropR, value);
                                break;
                            case "incCriticaldamageMin":
                                io.addStatValue(level, BaseStat.minCd, value);
                                break;
                            case "incCriticaldamageMax":
                                io.addStatValue(level, BaseStat.maxCd, value);
                                break;
                            case "incPADlv":
                                io.addStatValue(level, BaseStat.padLv, value);
                                break;
                            case "incMADlv":
                                io.addStatValue(level, BaseStat.madLv, value);
                                break;
                            case "incMHPlv":
                                io.addStatValue(level, BaseStat.mhpLv, value);
                                break;
                            case "incMMPlv":
                                io.addStatValue(level, BaseStat.mmpLv, value);
                                break;
                            case "prop":
                                io.addMiscValue(level, ItemOption.ItemOptionType.prop, value);
                                break;
                            case "face":
                                io.addMiscValue(level, ItemOption.ItemOptionType.face, value);
                                break;
                            case "time":
                                io.addMiscValue(level, ItemOption.ItemOptionType.time, value);
                                break;
                            case "HP":
                                io.addMiscValue(level, ItemOption.ItemOptionType.HP, value);
                                break;
                            case "MP":
                                io.addMiscValue(level, ItemOption.ItemOptionType.MP, value);
                                break;
                            case "attackType":
                                io.addMiscValue(level, ItemOption.ItemOptionType.attackType, value);
                                break;
                            case "level":
                                io.addMiscValue(level, ItemOption.ItemOptionType.level, value);
                                break;
                            case "ignoreDAM":
                                io.addMiscValue(level, ItemOption.ItemOptionType.ignoreDAM, value);
                                break;
                            case "ignoreDAMr":
                                io.addMiscValue(level, ItemOption.ItemOptionType.ignoreDAMr, value);
                                break;
                            case "DAMreflect":
                                io.addMiscValue(level, ItemOption.ItemOptionType.DAMreflect, value);
                                break;
                        }
                    }

                }
            }
            if (io.getWeight() == 0) {
                io.setWeight(1);
            }
            getItemOptions().put(io.getId(), io);
        }
    }

    public static Map<Integer, ItemOption> getItemOptions() {
        return itemOptions;
    }

    public static List<ItemOption> getFilteredItemOptions() {
        return filteredItemOptions;
    }

    public static ItemOption getItemOptionById(int id) {
        return itemOptions.getOrDefault(id, null);
    }

   private static void createFilteredOptions(){
        Collection<ItemOption> data = getItemOptions().values();
        filteredItemOptions = data.stream().filter(io ->
                io.getId() % 1000 != 14 //Old Magic Def, now regular Def ; removed to not have duplicates
                && io.getId() != 14 //Old Magic Def, now regular Def ; removed to not have duplicates
                && io.getId() % 1000 != 54 //Old Magic Def%, now regular Def% ; removed to not have duplicates
                && (io.getId() % 1000 != 7 || io.getId() == 41007) //Old Accuracy, now Max HP ; removed to not have duplicates (41007 = Decent Speed Infusion For Gloves)
                && io.getId() != 7 //Old Accuracy, now Max HP ; removed to not have duplicates
                && (io.getId() % 1000 != 47 || io.getId() == 12047) //Old Accuracy%, now Max HP% ; removed to not have duplicates (12047 = Bonus Weapons STR% Rare)
                && io.getId() % 1000 != 8 //Old Avoid, now Max MP ; removed to not have duplicates
                && io.getId() != 8 //Old Accuracy, now Max HP ; removed to not have duplicates
                && (io.getId() % 1000 != 48 || io.getId() == 12048) //Old Avoid%, now Max MP% ; removed to not have duplicates (12048 = Bonus Weapons DEX% Rare)
                && io.getId() != 40081 //Flat AllStat
                && io.getId() % 1000 != 202 //Additional %HP Recovery ; removed to not have duplicates
                && io.getId() % 1000 != 207 //Additional %MP Recovery ; removed to not have duplicates
                && io.getId() != 10222 //Secondary Rare-Prime 20% Poison Chance - WeaponsEmblemSecondary
                && io.getId() != 10227 //Secondary Rare-Prime 10% Stun Chance - WeaponsEmblemSecondary
                && io.getId() != 10232 //Secondary Rare-Prime 20% Slow Chance - WeaponsEmblemSecondary
                && io.getId() != 10237 //Secondary Rare-Prime 20% Blind Chance - WeaponsEmblemSecondary
                && io.getId() != 10242 //Secondary Rare-Prime 10% Freeze Chance - WeaponsEmblemSecondary
                && io.getId() != 10247 //Secondary Rare-Prime 10% Seal Chance - WeaponsEmblemSecondary
                && io.getId() % 1000 != 801 //Old Damage Cap Increase, now AllStat/Ignore Enemy Defense/AllStat%/Abnormal Status Res
                && io.getId() % 1000 != 802 //Old Damage Cap Increase, now AllStat%/ElementalResist
                && io.getId() % 10000 != 2056 //Old CritRate/Magic Def%, now AllStat%/ElementalResist ; removed to not have duplicates
                && io.getId() != 32661 //EXP Obtained
                && io.getId() != 42661 //EXP Obtained
                && io.getId() != 20396 //Duplicate "invincible for additional seconds"
                && io.getId() != 40057 //Glove's Crit Damage Duplicate
                && io.getId() != 42061 //Bonus - Glove's Crit Damage Duplicate
                && io.getId() != 42062 //Bonus - Armor's 1% Crit Damage Duplicate
                && io.getId() != 22056 //Bonus - Non-Weapon Crit Rate%
                && io.getId() != 32052 //Bonus - Non-Weapon Attack%
                && io.getId() != 32054 //Bonus - Non-Weapon MagicAttack%
                && io.getId() != 32058 //Bonus - Non-Weapon Crit Rate%
                && io.getId() != 32071 //Bonus - Non-Weapon Damage%
                && io.getId() != 42052 //Bonus - Non-Weapon Attack%
                && io.getId() != 42054 //Bonus - Non-Weapon MagicAttack%
                && io.getId() != 42058 //Bonus - Non-Weapon Crit Rate%
                && io.getId() != 42071 //Bonus - Non-Weapon Damage%
                && !(io.getId() > 14 && io.getId() < 900) //Rare Junk Filter
                && !(io.getId() > 20000 && io.getId() < 20014) //No Flat Stats Above Rare
                && !(io.getId() > 30000 && io.getId() < 30014) //No Flat Stats Above Rare
                && !(io.getId() > 40000 && io.getId() < 40014) //No Flat Stats Above Rare
        ).collect(Collectors.toList());

    }

    @Loader(varName = "startingItems")
    private static void loadStartingItemsFromWZ() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Etc.nx";
        NXNode makeCharInfo = new LazyNXFile(nxDir).resolve("MakeCharInfo.img");
        startingItems.addAll(searchForStartingItems(makeCharInfo));
    }

    private static Set<Integer> searchForStartingItems(NXNode n) {
        for (NXNode node : n) {
            String name = node.getName();
            Object valueObj = node.get();
            if (Util.isNumber(name) && (valueObj != null && Util.isNumber(valueObj.toString()))) {
                startingItems.add(Integer.parseInt(valueObj.toString()));
            }
            startingItems.addAll(searchForStartingItems(node));
        }
        return startingItems;
    }

    @SuppressWarnings("unused") // Reflection
    public static void generateDatFiles() {
        log.info("Started loading item data.");
        long start = System.currentTimeMillis();
        try {
            loadEquipsFromWz();
            loadMountItemsFromFile();
            loadItemsFromWZ();
            loadPetsFromWZ();
            loadItemOptionsFromWZ();
            loadStartingItemsFromWZ();
            QuestData.linkItemData();
            log.info(String.format("Completed generating item data in %dms.", System.currentTimeMillis() - start));
        } catch (IOException e) {
            log.error("There was an error loading NX Files. Shutting down~");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        generateDatFiles();
    }

    public static Map<Integer, ItemInfo> getItems() {
        return items;
    }

    public static void addItemInfo(ItemInfo ii) {
        getItems().put(ii.getItemId(), ii);
    }

    private static Map<Integer, PetInfo> getPets() {
        return pets;
    }

    public static void addPetInfo(PetInfo pi) {
        getPets().put(pi.getItemID(), pi);
    }

    public static void clear() {
        getEquips().clear();
        getItems().clear();
        getItemOptions().clear();
    }

    public static boolean isStartingItems(int[] items) {
        for (int item : items) {
            if (!isStartingItem(item)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStartingItem(int item) {
        return startingItems.contains(item);
    }

    // Utility functions to build shit
    private static Equip nodeToEquip(NXNode eqpNode) {
        String mainName = eqpNode.getName();

        if (mainName != null) {
            int itemId = Integer.parseInt(eqpNode.getName().replace(".img", ""));
            Equip equip = new Equip();
            equip.setItemId(itemId);
            equip.setInvType(InvType.EQUIP);
            equip.setType(Item.Type.EQUIP);
            equip.setDateExpire(FileTime.fromType(FileTime.Type.MAX_TIME));
            List<Integer> options = new ArrayList<>(7);
            for (NXNode info : eqpNode.getChild("info")) {
                String name = info.getName();
                String value = "";
                if(info.get() != null)
                    value = info.get().toString();

                switch (name) {
                    case "islot":
                        equip.setiSlot(value);
                        break;
                    case "vslot":
                        equip.setvSlot(value);
                        break;
                    case "reqJob":
                        equip.setrJob(Short.parseShort(value));
                        break;
                    case "reqLevel":
                        equip.setrLevel(Short.parseShort(value));
                        break;
                    case "reqSTR":
                        equip.setrStr(Short.parseShort(value));
                        break;
                    case "reqDEX":
                        equip.setrDex(Short.parseShort(value));
                        break;
                    case "reqINT":
                        equip.setrInt(Short.parseShort(value));
                        break;
                    case "reqPOP":
                        equip.setrPop(Short.parseShort(value));
                        break;
                    case "incSTR":
                        equip.setiStr(Short.parseShort(value));
                        break;
                    case "incDEX":
                        equip.setiDex(Short.parseShort(value));
                        break;
                    case "incINT":
                        equip.setiInt(Short.parseShort(value));
                        break;
                    case "incLUK":
                        equip.setiLuk(Short.parseShort(value));
                        break;
                    case "incPDD":
                        equip.setiPDD(Short.parseShort(value));
                        break;
                    case "incMDD":
                        equip.setiMDD(Short.parseShort(value));
                        break;
                    case "incMHP":
                        equip.setiMaxHp(Short.parseShort(value));
                        break;
                    case "incMMP":
                        equip.setiMaxMp(Short.parseShort(value));
                        break;
                    case "incPAD":
                        equip.setiPad(Short.parseShort(value));
                        break;
                    case "incMAD":
                        equip.setiMad(Short.parseShort(value));
                        break;
                    case "incEVA":
                        equip.setiEva(Short.parseShort(value));
                        break;
                    case "incACC":
                        equip.setiAcc(Short.parseShort(value));
                        break;
                    case "incSpeed":
                        equip.setiSpeed(Short.parseShort(value));
                        break;
                    case "incJump":
                        equip.setiJump(Short.parseShort(value));
                        break;
                    case "damR":
                        equip.setDamR(Short.parseShort(value));
                        break;
                    case "statR":
                        equip.setStatR(Short.parseShort(value));
                        break;
                    case "imdR":
                        equip.setImdr(Short.parseShort(value));
                        break;
                    case "bdR":
                        equip.setBdr(Short.parseShort(value));
                        break;
                    case "tuc":
                        equip.setTuc(Short.parseShort(value));
                        break;
                    case "IUCMax":
                        equip.setHasIUCMax(true);
                        equip.setIUCMax(Short.parseShort(value));
                        break;
                    case "setItemID":
                        equip.setSetItemID(Integer.parseInt(value));
                        break;
                    case "price":
                        equip.setPrice(Integer.parseInt(value));
                        break;
                    case "attackSpeed":
                        equip.setAttackSpeed(Integer.parseInt(value));
                        break;
                    case "cash":
                        equip.setCash(Integer.parseInt(value) != 0);
                        break;
                    case "expireOnLogout":
                        equip.setExpireOnLogout(Integer.parseInt(value) != 0);
                        break;
                    case "exItem":
                        equip.setExItem(Integer.parseInt(value) != 0);
                        break;
                    case "notSale":
                        equip.setNotSale(Integer.parseInt(value) != 0);
                        break;
                    case "only":
                        equip.setOnly(Integer.parseInt(value) != 0);
                        break;
                    case "tradeBlock":
                        equip.setTradeBlock(Integer.parseInt(value) != 0);
                        break;
                    case "fixedPotential":
                        equip.setFixedPotential(Integer.parseInt(value) != 0);
                        break;
                    case "noPotential":
                        equip.setNoPotential(Integer.parseInt(value) != 0);
                        break;
                    case "bossReward":
                        equip.setBossReward(Integer.parseInt(value) != 0);
                        break;
                    case "superiorEqp":
                        equip.setSuperiorEqp(Integer.parseInt(value) != 0);
                        break;
                    case "reduceReq":
                        equip.setiReduceReq(Short.parseShort(value));
                        break;
                    case "fixedGrade":
                        equip.setFixedGrade(Integer.parseInt(value));
                        break;
                    case "specialGrade":
                        equip.setSpecialGrade(Integer.parseInt(value));
                        break;
                    case "charmEXP":
                        equip.setCharmEXP(Integer.parseInt(value));
                        break;
                    case "android":
                        equip.setAndroid(Integer.parseInt(value));
                        break;
                    case "grade":
                        equip.setAndroidGrade(Integer.parseInt(value));
                        break;
                }
                for (int i = 0; i < 7 - options.size(); i++) {
                    options.add(0);
                }
                equip.setOptions(options);
            }
            return equip;
        }
        return null;
    }

    private static ItemInfo nodeToItem(NXNode itemNode, String category) {
        String nodeName = itemNode.getName();

        if (!Util.isNumber(nodeName)) {
            if (LOG_UNKS) {
                log.error(String.format("%s is not a number.", nodeName));
            }
            return null;
        }

        int id = Integer.parseInt(nodeName);
        ItemInfo item = new ItemInfo();
        item.setItemId(id);
        item.setInvType(InvType.getInvTypeByString(category));
        NXNode itemInfo = itemNode.getChild("info");
        if (itemInfo != null && itemInfo.get() != null) {
            for (NXNode info : itemInfo) {
                String name = itemInfo.getName();
                String value = itemInfo.get().toString();
                int intValue = 0;
                if (Util.isInteger(value)) {
                    intValue = Integer.parseInt(value);
                }
                switch (name) {
                    case "cash":
                        item.setCash(intValue != 0);
                        break;
                    case "price":
                        item.setPrice(intValue);
                        break;
                    case "slotMax":
                        item.setSlotMax(intValue);
                        break;
                    // info not currently interesting. May be interesting in the future.
                    case "icon":
                    case "iconRaw":
                    case "iconD":
                    case "iconReward":
                    case "iconShop":
                    case "recoveryHP":
                    case "recoveryMP":
                    case "sitAction":
                    case "bodyRelMove":
                    case "only":
                    case "noDrop":
                    case "timeLimited":
                    case "accountSharable":
                    case "nickTag":
                    case "nickSkill":
                    case "endLotteryDate":
                    case "noFlip":
                    case "noMoveToLocker":
                    case "soldInform":
                    case "purchaseShop":
                    case "flatRate":
                    case "limitMin":
                    case "protectTime":
                    case "maxDays":
                    case "reset":
                    case "replace":
                    case "expireOnLogout":
                    case "max":
                    case "lvOptimum":
                    case "lvRange":
                    case "limitedLv":
                    case "tradeReward":
                    case "type":
                    case "floatType":
                    case "message":
                    case "pquest":
                    case "bonusEXPRate":
                    case "notExtend":
                        break;

                    case "skill":
                        for (NXNode masteryBookSkillIdNode : info) {
                            item.addSkill(Integer.parseInt(masteryBookSkillIdNode.get().toString()));
                        }
                        break;
                    case "reqSkillLevel":
                        item.setReqSkillLv(intValue);
                        break;
                    case "masterLevel":
                        item.setMasterLv(intValue);
                        break;

                    case "stateChangeItem":
                    case "direction":
                    case "exGrade":
                    case "exGradeWeight":
                    case "effect":
                    case "bigSize":
                    case "nickSkillTimeLimited":
                    case "StarPlanet":
                    case "useTradeBlock":
                    case "commerce":
                    case "invisibleWeapon":
                    case "sitEmotion":
                    case "sitLeft":
                    case "tamingMob":
                    case "textInfo":
                    case "lv":
                    case "tradeAvailable":
                    case "pickUpBlock":
                    case "rewardItemID":
                    case "autoPrice":
                    case "selectedSlot":
                    case "minusLevel":
                    case "addTime":
                    case "reqLevel":
                    case "waittime":
                    case "buffchair":
                    case "cooltime":
                    case "consumeitem":
                    case "distanceX":
                    case "distanceY":
                    case "maxDiff":
                    case "maxDX":
                    case "levelDX":
                    case "maxLevel":
                    case "exp":
                    case "dropBlock":
                    case "dropExpireTime":
                    case "animation_create":
                    case "animation_dropped":
                    case "noCancelMouse":
                    case "soulItemType":
                    case "Rate":
                    case "unitPrice":
                    case "delayMsg":
                    case "bridlePropZeroMsg":
                    case "nomobMsg":
                    case "bridleProp":
                    case "bridlePropChg":
                    case "bridleMsgType":
                    case "left":
                    case "right":
                    case "top":
                    case "bottom":
                    case "useDelay":
                    case "name":
                    case "uiData":
                    case "UI":
                    case "recoveryRate":
                    case "itemMsg":
                    case "noRotateIcon":
                    case "endUseDate":
                    case "noSound":
                    case "slotMat":
                    case "isBgmOrEffect":
                    case "bgmPath":
                    case "repeat":
                    case "NoCancel":
                    case "rotateSpeed":
                    case "gender":
                    case "life":
                    case "pickupItem":
                    case "add":
                    case "consumeHP":
                    case "longRange":
                    case "dropSweep":
                    case "pickupAll":
                    case "ignorePickup":
                    case "consumeMP":
                    case "autoBuff":
                    case "smartPet":
                    case "giantPet":
                    case "shop":
                    case "recall":
                    case "autoSpeaking":
                    case "consumeCure":
                    case "meso":
                    case "maplepoint":
                    case "rate":
                    case "overlap":
                    case "lt":
                    case "rb":
                    case "path4Top":
                    case "jumplevel":
                    case "slotIndex":
                    case "addDay":
                    case "incLEV":
                    case "cashTradeBlock":
                    case "dressUpgrade":
                    case "skillEffectID":
                    case "emotion":
                    case "tradBlock":
                    case "tragetBlock":
                    case "scanTradeBlock":
                    case "mobPotion":
                    case "ignoreTendencyStatLimit":
                    case "effectByItemID":
                    case "pachinko":
                    case "iconEnter":
                    case "iconLeave":
                    case "noMoveIcon":
                    case "noShadow":
                    case "preventslip":
                    case "warmsupport":
                    case "reqCUC":
                    case "incCraft":
                    case "reqEquipLevelMin":
                    case "incPVPDamage":
                    case "successRates":
                    case "enchantCategory":
                    case "additionalSuccess":
                    case "level":
                    case "specialItem":
                    case "exNew":
                    case "cuttable":
                    case "perfectReset":
                    case "resetRUC":
                    case "incMax":
                    case "noSuperior":
                    case "noRecovery":
                    case "reqMap":
                    case "random":
                    case "limit":
                    case "cantAccountSharable":
                    case "LvUpWarning":
                    case "canAccountSharable":
                    case "canUseJob":
                    case "createPeriod":
                    case "iconLarge":
                    case "morphItem":
                    case "consumableFrom":
                    case "noExpend":
                    case "sample":
                    case "notPickUpByPet":
                    case "sharableOnce":
                    case "bonusStageItem":
                    case "sampleOffsetY":
                    case "runOnPickup":
                    case "noSale":
                    case "skillCast":
                    case "activateCardSetID":
                    case "summonSoulMobID":
                    case "cursor":
                    case "karma":
                    case "pointCost":
                    case "itemPoint":
                    case "sharedStatCostGrade":
                    case "levelVariation":
                    case "accountShareable":
                    case "extendLimit":
                    case "showMessage":
                    case "mcType":
                    case "consumeItem":
                    case "hybrid":
                    case "mobId":
                    case "lvMin":
                    case "lvMax":
                    case "picture":
                    case "ratef":
                    case "time":
                    case "reqGuildLevel":
                    case "guild":
                    case "randEffect":
                    case "accountShareTag":
                    case "removeEffect":
                    case "forcingItem":
                    case "fixFrameIdx":
                    case "buffItemID":
                    case "removeCharacterInfo":
                    case "nameInfo":
                    case "bgmInfo":
                    case "flip":
                    case "pos":
                    case "randomChair":
                    case "maxLength":
                    case "continuity":
                    case "specificDX":
                    case "groupTWInfo":
                    case "face":
                    case "removeBody":
                    case "mesoChair":
                    case "towerBottom":
                    case "towerTop":
                    case "topOffset":
                    case "craftEXP":
                    case "willEXP":
                        break;
                    case "tradeBlock":
                        item.setTradeBlock(intValue != 0);
                        break;
                    case "notSale":
                        item.setNotSale(intValue != 0);
                        break;
                    case "path":
                        item.setPath(value);
                        break;
                    case "noCursed":
                        item.setNoCursed(intValue != 0);
                        break;
                    case "noNegative":
                        item.putScrollStat(noNegative, intValue);
                        break;
                    case "incRandVol":
                        item.putScrollStat(incRandVol, intValue);
                        break;
                    case "success":
                        item.putScrollStat(success, intValue);
                        break;
                    case "incSTR":
                        item.putScrollStat(incSTR, intValue);
                        break;
                    case "incDEX":
                        item.putScrollStat(incDEX, intValue);
                        break;
                    case "incINT":
                        item.putScrollStat(incINT, intValue);
                        break;
                    case "incLUK":
                        item.putScrollStat(incLUK, intValue);
                        break;
                    case "incPAD":
                        item.putScrollStat(incPAD, intValue);
                        break;
                    case "incMAD":
                        item.putScrollStat(incMAD, intValue);
                        break;
                    case "incPDD":
                        item.putScrollStat(incPDD, intValue);
                        break;
                    case "incMDD":
                        item.putScrollStat(incMDD, intValue);
                        break;
                    case "incEVA":
                        item.putScrollStat(incEVA, intValue);
                        break;
                    case "incACC":
                        item.putScrollStat(incACC, intValue);
                        break;
                    case "incPERIOD":
                        item.putScrollStat(incPERIOD, intValue);
                        break;
                    case "incMHP":
                    case "incMaxHP":
                        item.putScrollStat(incMHP, intValue);
                        break;
                    case "incMMP":
                    case "incMaxMP":
                        item.putScrollStat(incMMP, intValue);
                        break;
                    case "incSpeed":
                        item.putScrollStat(incSpeed, intValue);
                        break;
                    case "incJump":
                        item.putScrollStat(incJump, intValue);
                        break;
                    case "incReqLevel":
                        item.putScrollStat(incReqLevel, intValue);
                        break;
                    case "randOption":
                        item.putScrollStat(randOption, intValue);
                        break;
                    case "randstat":
                    case "randStat":
                        item.putScrollStat(randStat, intValue);
                        break;
                    case "tuc":
                        item.putScrollStat(tuc, intValue);
                        break;
                    case "incIUC":
                        item.putScrollStat(incIUC, intValue);
                        break;
                    case "speed":
                        item.putScrollStat(speed, intValue);
                        break;
                    case "forceUpgrade":
                        item.putScrollStat(forceUpgrade, intValue);
                        break;
                    case "cursed":
                        item.putScrollStat(cursed, intValue);
                        break;
                    case "maxSuperiorEqp":
                        item.putScrollStat(maxSuperiorEqp, intValue);
                        break;
                    case "reqRUC":
                        item.putScrollStat(reqRUC, intValue);
                        break;
                    case "bagType":
                        item.setBagType(intValue);
                        break;
                    case "charmEXP":
                    case "charismaEXP":
                        item.setCharmEXP(intValue);
                        break;
                    case "senseEXP":
                        item.setSenseEXP(intValue);
                        break;
                    case "quest":
                        item.setQuest(intValue != 0);
                        break;
                    case "reqQuestOnProgress":
                        item.setReqQuestOnProgress(intValue);
                        break;
                    case "qid":
                    case "questId":
                        if (value.contains(".") && value.split("[.]").length > 0) {
                            item.addQuest(Integer.parseInt(value.split("[.]")[0]));
                        } else {
                            item.addQuest(intValue);
                        }
                        break;
                    case "notConsume":
                        item.setNotConsume(intValue != 0);
                        break;
                    case "monsterBook":
                        item.setMonsterBook(intValue != 0);
                        break;
                    case "mob":
                        item.setMobID(intValue);
                        break;
                    case "npc":
                        item.setNpcID(intValue);
                        break;
                    case "linkedID":
                        item.setLinkedID(intValue);
                        break;
                    case "reqEquipLevelMax":
                        item.putScrollStat(reqEquipLevelMax, intValue);
                        break;
                    case "createType":
                        item.putScrollStat(createType, intValue);
                        break;
                    case "optionType":
                        item.putScrollStat(optionType, intValue);
                        break;
                    case "grade":
                        item.setGrade(intValue);
                        break;
                    case "android":
                        item.setAndroid(intValue);
                        break;
                    case "spec":
                        break;
                    case "recover":
                        item.putScrollStat(recover, intValue);
                        break;
                    case "setItemCategory":
                        item.putScrollStat(setItemCategory, intValue);
                        break;
                    case "create":
                        item.setCreateID(intValue);
                        break;
                    case "mobHP":
                        item.setMobHP(intValue);
                        break;
                    default:
                        if (LOG_UNKS) {
                            log.warn(String.format("Unknown node: %s, value = %s, itemID = %s", name, value, item.getItemId()));
                        }
                }
            }
        }

        NXNode reqNode = itemNode.getChild("req");
        if (reqNode != null) {
            for (NXNode req : reqNode) {
                String name = req.getName();
                String value = req.get().toString();
                item.getReqItemIds().add(Integer.parseInt(value));
            }
        }
        NXNode socket = itemNode.getChild("socket");
        if (socket != null) {
            for (NXNode socketNode : socket) {
                String name = socketNode.getName();
                switch (name) {
                    case "optionType":
                        String value = socketNode.get().toString();
                        item.putScrollStat(optionType, Integer.parseInt(value));
                        break;
                }
            }

            //NXNode option = socket.getChild("option").getChild("0");
            //NXNode optionString = option.getChild("optionString");
            NXNode optionString = socket.getChild("option/0/optionString");
            String ssName = "";
            if (optionString != null && optionString.get() != null) {
                ssName = optionString.get().toString();
            }

            NXNode level = socket.getChild("option/0/level");
            int ssVal = 0;
            if (level != null && level.get() != null) {
                ssVal = (int) level.get();
            }
            if(ScrollStat.getScrollStatByString(ssName) != null) {
                item.putScrollStat(ScrollStat.valueOf(ssName), ssVal);
            } else {
                log.info("non existent scroll stat" + ssName);
            }
        }
        NXNode spec = itemNode.getChild("spec");
        if (spec != null) {
            for (NXNode specNode : spec) {
                String name = specNode.getName();
                String value = "";
                if(specNode.get() != null){
                    value = specNode.get().toString();
                }
                switch (name) {
                    case "script":
                        item.setScript(value);
                        break;
                    case "npc":
                        item.setScriptNPC(Integer.parseInt(value));
                        break;
                    case "moveTo":
                        item.setMoveTo(Integer.parseInt(value));
                        break;
                    default:
                        SpecStat ss = SpecStat.getSpecStatByName(name);
                        if (ss != null && value != null) {
                            item.putSpecStat(ss, Integer.parseInt(value));
                        } else if (LOG_UNKS){
                            log.warn(String.format("Unhandled spec for id %d, name %s, value %s", id, name, value));
                        }
                }
            }
        }

        NXNode reward = itemNode.getChild("reward");
        if (reward != null) {
            for (NXNode rewardNode : reward) {
                ItemRewardInfo iri = new ItemRewardInfo();
                for (NXNode rewardInfoNode : rewardNode) {
                    String name = rewardInfoNode.getName();
                    String value = rewardInfoNode.get().toString();
                    if (value == null) {
                        continue;
                    }
                    value = value.replace("\n", "").replace("\r", "")
                                 .replace("\\n", "").replace("\\r", "") // unluko
                                 .replace("[R8]", "");
                    switch (name) {
                        case "count":
                            iri.setCount(Integer.parseInt(value));
                            break;
                        case "item":
                            iri.setItemID(Integer.parseInt(value));
                            break;
                        case "prob":
                            iri.setProb(Double.parseDouble(value));
                            break;
                        case "period":
                            iri.setPeriod(Integer.parseInt(value));
                            break;
                        case "effect":
                        case "Effect":
                            iri.setEffect(value);
                            break;
                    }
                }
                item.addItemReward(iri);
            }
        }
        item.setSkillId(getSkillIdByItemId(id));
        return item;
    }


}
