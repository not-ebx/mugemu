package net.mugeemu.ms.loaders;

import net.mugeemu.ms.loaders.containerclasses.SkillStringInfo;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.util.Loader;
import net.mugeemu.ms.util.Util;
import org.apache.log4j.LogManager;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.*;

/**
 * Created on 1/11/2018.
 */
public class StringData {
    public static Map<Integer, SkillStringInfo> skillString = new HashMap<>();
    public static Map<Integer, String> itemStrings = new HashMap<>();
    public static Map<Integer, String> mapStrings = new HashMap<>();
    public static Map<Integer, String> mobStrings = new HashMap<>(); // name + health
    public static Map<Integer, String> npcStrings = new HashMap<>();

    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();

    public static Map<Integer, String> getItemStrings() {
        return itemStrings;
    }

    public static Map<Integer, String> getMapStrings() {
        return mapStrings;
    }

    public static Map<Integer, String> getMobStrings() {
        return mobStrings;
    }

    public static Map<Integer, String> getNpcStrings() {
        return npcStrings;
    }

    public static void loadItemStringsFromWz() throws IOException {
        log.info("Started loading item strings from wz.");
        long start = System.currentTimeMillis();
        String nxDir = ServerConstants.NX_DIR + "/String.nx";
        String[] categories = new String[]{
            "Cash.img",
            "Consume.img",
            "Ins.img",
            "Pet.img",
            "Etc.img/Etc",
            "Eqp.img/Eqp/Accessory",
            "Eqp.img/Eqp/Android",
            "Eqp.img/Eqp/Cap",
            "Eqp.img/Eqp/Cape",
            "Eqp.img/Eqp/Coat",
            "Eqp.img/Eqp/Dragon",
            "Eqp.img/Eqp/Face",
            "Eqp.img/Eqp/Glove",
            "Eqp.img/Eqp/Hair",
            "Eqp.img/Eqp/Longcoat",
            "Eqp.img/Eqp/Mechanic",
            "Eqp.img/Eqp/MonsterBook",
            "Eqp.img/Eqp/Pants",
            "Eqp.img/Eqp/PetEquip",
            "Eqp.img/Eqp/Ring",
            "Eqp.img/Eqp/Shield",
            "Eqp.img/Eqp/Shoes",
            "Eqp.img/Eqp/Taming",
            "Eqp.img/Eqp/Weapon",
        };

        for(String category : categories) {
            NXNode categoryNode = new LazyNXFile(nxDir).resolve(category);
            if(categoryNode == null){
                continue;
            }
            for (NXNode categoryChildNode: categoryNode) {
                try {
                    int id = Integer.parseInt(categoryChildNode.getName());
                    String name = (String) (categoryChildNode.getChild("name") != null
                        ? categoryChildNode.getChild("name").get() : null);

                    if (name != null)
                        itemStrings.put(id, name);
                } catch (Exception e) {
                    log.warn("Skipped " + categoryChildNode.getName() + " due to getting a value that was not string");
                }
            }
        }

        log.info(String.format("Loaded item strings from wz in %dms.", System.currentTimeMillis() - start));
    }

    public static void loadSkillStringsFromWz() throws IOException {
        log.info("Started loading skill strings from wz.");
        long start = System.currentTimeMillis();
        String nxDir = ServerConstants.NX_DIR + "/String.nx";
        NXNode skills = new LazyNXFile(nxDir).resolve("Skill.img");

        for (NXNode skill : skills) {
            NXNode bookNameNode = skill.getChild("bookName");
            if (bookNameNode != null) {
                continue;
            }

            SkillStringInfo ssi = new SkillStringInfo();
            String name = "";
            if (skill.hasChild("name")) {
                name = (String)skill.getChild("name").get();
                ssi.setName((String)skill.getChild("name").get());
            }

            if (skill.hasChild("desc")) {
                ssi.setDesc((String)skill.getChild("desc").get());
            }
            if (skill.hasChild("h")) {
                ssi.setH((String)skill.getChild("h").get());
            } else {
                if (skill.hasChild("h1")) {
                    ssi.setH((String)skill.getChild("h1").get());
                }
            }

            if(Util.isNumber(name))
                skillString.put(Integer.valueOf(name), ssi);
        }
        log.info(String.format("Loaded skill strings in %dms.", System.currentTimeMillis() - start));
    }

    public static void loadLifeFromWz() throws IOException {
        log.info("Started loading Life strings from wz.");
        long start = System.currentTimeMillis();
        String nxDir = ServerConstants.NX_DIR + "/String.nx";
        String[] lifeType = {"Mob.img", "Npc.img"};

        for (String type : lifeType){
            NXNode lives = new LazyNXFile(nxDir).resolve(type);

            for (NXNode life : lives) {
                int id = Integer.parseInt(life.getName());
                if (!life.hasChild("name")) {
                    continue;
                }

                String name = (String) life.getChild("name").get();
                if (type.equals("Mob.img"))
                    getMobStrings().put(id, name);
                else
                    getNpcStrings().put(id, name);
            }
        }
        log.info(String.format("Loaded mob strings in %dms.", System.currentTimeMillis() - start));
    }

    public static void loadMapStringsFromWz() throws IOException {
        log.info("Started loading map strings from wz.");
        long start = System.currentTimeMillis();
        String nxDir = ServerConstants.NX_DIR + "/String.nx";

        NXNode mapImg = new LazyNXFile(nxDir).resolve("Map.img");

        for (NXNode mapContinent: mapImg) {
            for (NXNode map : mapContinent) {
                int id = Integer.parseInt(map.getName());
                String mapName = "UNK";
                String streetName = "UNK";

                if (map.hasChild("name")) {
                    mapName = (String) map.getChild("name").get();
                }

                if (map.hasChild("streetName")) {
                    streetName = (String) map.getChild("streetName").get();
                }

                getMapStrings().put(id, String.format("%s : %s", streetName, mapName));
            }
        }
        log.info(String.format("Loaded map strings in %dms.", System.currentTimeMillis() - start));
    }

    public static Map<Integer, SkillStringInfo> getSkillString() {
        return skillString;
    }

    public static void generateDatFiles() {
        load();
    }

    public static void main(String[] args) {
        log.info("Started generating string data.");
        long start = System.currentTimeMillis();
        try {
            loadSkillStringsFromWz();
            loadItemStringsFromWz();
            loadLifeFromWz(); // Loads both npc and mobs lmao
            loadMapStringsFromWz();
            log.info(String.format("Completed generating string data in %dms.", System.currentTimeMillis() - start));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public static SkillStringInfo getSkillStringById(int id) {
        return getSkillString().getOrDefault(id, null);
    }

    public static String getItemStringById(int id) {
        return getItemStrings().getOrDefault(id, null);
    }

    public static String getMobStringById(int id) {
        return getMobStrings().getOrDefault(id, null);
    }

    public static String getNpcStringById(int id) {
        return getNpcStrings().getOrDefault(id, null);
    }

    public static String getMapStringById(int id) {
        return getMapStrings().getOrDefault(id, null);
    }

    public static Map<Integer, String> getItemStringByName(String query) {
        query = query.toLowerCase();
        Map<Integer, String> res = new HashMap<>();
        for (Map.Entry<Integer, String> entry : itemStrings.entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            if(name == null) {
                continue;
            }
            String ssName = name.toLowerCase();
            if (ssName.contains(query)) {
                res.put(id, name);
            }
        }
        return res;
    }


    public static Map<Integer, SkillStringInfo> getSkillStringByName(String query) {
        Map<Integer, SkillStringInfo> res = new HashMap<>();
        for (Map.Entry<Integer, SkillStringInfo> entry : StringData.getSkillString().entrySet()) {
            int id = entry.getKey();
            SkillStringInfo ssi = entry.getValue();
            if(ssi.getName() == null) {
                continue;
            }
            String ssName = ssi.getName().toLowerCase();
            if (ssName.contains(query)) {
                res.put(id, ssi);
            }
        }
        return res;
    }

    public static Map<Integer, String> getMobStringByName(String query) {
        query = query.toLowerCase();
        Map<Integer, String> res = new HashMap<>();
        for (Map.Entry<Integer, String> entry : getMobStrings().entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            if(name == null) {
                continue;
            }
            String ssName = name.toLowerCase();
            if (ssName.contains(query)) {
                res.put(id, name);
            }
        }
        return res;
    }

    public static Map<Integer, String> getNpcStringByName(String query) {
        query = query.toLowerCase();
        Map<Integer, String> res = new HashMap<>();
        for (Map.Entry<Integer, String> entry : getNpcStrings().entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            if(name == null) {
                continue;
            }
            String ssName = name.toLowerCase();
            if (ssName.contains(query)) {
                res.put(id, name);
            }
        }
        return res;
    }

    public static Map<Integer, String> getMapStringByName(String query) {
        query = query.toLowerCase();
        Map<Integer, String> res = new HashMap<>();
        for (Map.Entry<Integer, String> entry : getMapStrings().entrySet()) {
            int id = entry.getKey();
            String name = entry.getValue();
            if(name == null) {
                continue;
            }
            String ssName = name.toLowerCase();
            if (ssName.contains(query)) {
                res.put(id, name);
            }
        }
        return res;
    }

    public static void clear() {
        getSkillString().clear();
        getItemStrings().clear();
        getMobStrings().clear();
        getNpcStrings().clear();
        getMapStrings().clear();
    }

    public static void load() {
        log.info("Started generating string data.");
        long start = System.currentTimeMillis();
        try {
            loadSkillStringsFromWz();
            loadItemStringsFromWz();
            loadLifeFromWz(); // Loads both npc and mobs lmao
            loadMapStringsFromWz();
            log.info(String.format("Completed generating string data in %dms.", System.currentTimeMillis() - start));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
