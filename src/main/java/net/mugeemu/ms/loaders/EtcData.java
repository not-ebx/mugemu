package net.mugeemu.ms.loaders;

import net.mugeemu.ms.loaders.containerclasses.AndroidInfo;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.client.character.items.BossSoul;
import net.mugeemu.ms.client.character.items.ItemOption;
import net.mugeemu.ms.client.character.items.SetEffect;
import net.mugeemu.ms.enums.ScrollStat;
import net.mugeemu.ms.enums.SoulType;
import net.mugeemu.ms.util.Loader;
import net.mugeemu.ms.util.Saver;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.util.XMLApi;
import net.mugeemu.ms.util.container.Tuple;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtcData {

    private static final Logger log = Logger.getLogger(EtcData.class);
    private static final Map<Integer, Integer> familiarSkills = new HashMap<>();
    private static final Map<Integer, SetEffect> setEffects = new HashMap<>();
    private static final Map<Integer, Integer> characterCards = new HashMap<>();
    private static Map<Integer, AndroidInfo> androidInfo = new HashMap<>();

    private static final String SCROLL_STAT_ID = "1";
    private static final String ITEM_OPTION_ID = "2";

    public static AndroidInfo getAndroidInfoById(int id){
        return androidInfo.getOrDefault(id, null);
    }

    public static void loadAndroidsFromWz() throws IOException {
        NXNode androids = new LazyNXFile(ServerConstants.NX_DIR + "/Etc.nx").resolve("Android");
        for (NXNode android : androids) {
            AndroidInfo ai = new AndroidInfo(
                Integer.parseInt(android.getName().replace(".img", ""))
            );
            for (NXNode androidData : android) {
                String mainName = androidData.getName();
                switch (mainName) {
                    case "costume":
                        for (NXNode n : androidData) {
                            String nName = n.getName();
                            switch (nName) {
                                case "face":
                                    for (NXNode inner : n) {
                                        ai.addFace(Integer.parseInt(inner.get().toString()) % 10000);
                                    }
                                    break;
                                case "hair":
                                    for (NXNode inner : n) {
                                        ai.addHair(Integer.parseInt(inner.get().toString()) % 10000);
                                    }
                                    break;
                                case "skin":
                                    for (NXNode inner : n) {
                                        ai.addSkin(Integer.parseInt(inner.get().toString()) % 1000);
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
            androidInfo.put(ai.getId(), ai);
        }
    }

    public static void loadSetEffectsFromWz() throws IOException {
        NXNode sets = new LazyNXFile(ServerConstants.NX_DIR + "/Etc.nx").resolve("SetItemInfo.img");
        for (NXNode set: sets) {
            int setId = Integer.parseInt(set.getName());
            NXNode effectNode = set.getChild("Effect");

            for (NXNode effect : effectNode) {
                int level = Integer.parseInt(effect.getName());
                SetEffect setEffect = setEffects.getOrDefault(setId, new SetEffect());
                for (NXNode effectInfo : effect) {
                    String ssName = effectInfo.getName();
                    ScrollStat stat = ScrollStat.getScrollStatByString(ssName);
                    if (!ssName.equals("Option") && stat != null) {
                        int statAmount = Integer.parseInt(effectInfo.get().toString());
                        setEffect.addScrollStat(level, stat, statAmount);
                    } else if (ssName.equals("Option")) {
                        for (NXNode optionNode : effectInfo) {
                            for (NXNode option : optionNode) {
                                if(option.hasChild("level") && option.hasChild("option")) {
                                    int optionLevel = Integer.parseInt((option.getChild("level").get().toString()));
                                    int optionN = Integer.parseInt(option.getChild("option").get().toString());

                                    ItemOption io = new ItemOption();
                                    io.setId(optionN);
                                    io.setReqLevel(optionLevel);
                                    setEffect.addOption(level, io);
                                }
                            }
                        }
                    }
                }
                setEffects.put(setId, setEffect);
            }
        }
    }

    // Not used
    public static void loadCharacterCardsFromWz() {
        File file = new File(String.format("%s/Etc.wz/CharacterCard.img.xml", ServerConstants.WZ_DIR));
        Node root = XMLApi.getRoot(file);
        Node firstNode = XMLApi.getAllChildren(root).get(0);
        Node mainNode = XMLApi.getFirstChildByNameBF(firstNode, "Card");
        List<Node> nodes = XMLApi.getAllChildren(mainNode);
        for (Node node : nodes) {
            int jobId = Integer.parseInt(XMLApi.getNamedAttribute(node, "name")) * 10;
            Node skillNode = XMLApi.getFirstChildByNameBF(firstNode, "skillID");
            int skillId = Integer.parseInt(XMLApi.getNamedAttribute(skillNode, "value"));
            characterCards.put(jobId, skillId);
        }
    }

    public static SetEffect getSetEffectInfoById(int setID) {
        if (setEffects.containsKey(setID)) {
            return setEffects.get(setID);
        }
        return null;
    }


    public static void generateDatFiles() {
        log.info("Started generating etc data.");
        Util.makeDirIfAbsent(ServerConstants.DAT_DIR + "/etc");
        long start = System.currentTimeMillis();
        try {
            loadAndroidsFromWz();
            loadSetEffectsFromWz();
            loadFamiliarSkillsFromWz();
            //loadCharacterCardsFromWz();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(String.format("Completed generating etc data in %dms.", System.currentTimeMillis() - start));
    }

    public static void clear() {
        androidInfo.clear();
    }

    public static void main(String[] args) {
        generateDatFiles();
    }

    private static void loadFamiliarSkillsFromWz() throws IOException {
        NXNode familiarSkillsNode = new LazyNXFile(ServerConstants.NX_DIR + "/Etc.nx").resolve("FamiliarInfo.img");

        for (NXNode familiarSkill: familiarSkillsNode) {
            int id = Integer.parseInt(familiarSkill.getName());

            NXNode passiveSkill = familiarSkill.getChild("passive");
            if (passiveSkill!= null) {
                int skillID = Integer.parseInt( passiveSkill.get().toString());
                familiarSkills.put(id, skillID);
            }
        }
    }


    public static int getSkillByFamiliarID(int familiarID) {
        return familiarSkills.get(familiarID);
    }

}
