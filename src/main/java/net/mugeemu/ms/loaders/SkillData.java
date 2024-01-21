package net.mugeemu.ms.loaders;

import net.mugeemu.ms.loaders.containerclasses.MakingSkillRecipe;
import net.mugeemu.ms.loaders.containerclasses.MobSkillInfo;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.client.character.skills.Skill;
import net.mugeemu.ms.client.character.skills.SkillStat;
import net.mugeemu.ms.client.character.skills.info.SkillInfo;
import net.mugeemu.ms.life.mob.skill.MobSkillStat;
import net.mugeemu.ms.util.*;
import org.apache.log4j.LogManager;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created on 12/20/2017.
 */
public class SkillData {

    private static Map<Integer, SkillInfo> skills = new HashMap<>();
    private static Map<Short, Map<Short, MobSkillInfo>> mobSkillInfos = new HashMap<>();
    private static Map<Integer, MakingSkillRecipe> makingSkillRecipes = new HashMap<>();
    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();
    private static final boolean LOG_UNKS = false;

    private static void loadSkillsFromWz() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Skill.nx";
        NXNode skillNx = new LazyNXFile(nxDir).getRoot();
        for (NXNode rootNode : skillNx) {
            if (rootNode.getName().contains("Dragon")) {
                continue;
            }
            String rootIdStr = rootNode.getName().replace(".img", "");
            int rootId;
            if (Util.isNumber(rootIdStr)) {
                rootId = Integer.parseInt(rootIdStr);
            } else {
                continue;
            }

            Set<String> unkVals = new HashSet<>();
            if (!rootNode.hasChild("skill"))
                continue;

            for (NXNode skillNode : rootNode.getChild("skill")) {
                String skillIdStr = skillNode.getName();
                int skillId;
                if (Util.isNumber(skillIdStr)) {
                    SkillInfo skill = new SkillInfo();
                    skill.setRootId(rootId);
                    if (Util.isNumber(skillIdStr)) {
                        skillId = Integer.parseInt(skillIdStr);
                        skill.setSkillId(skillId);
                    } else {
                        if (LOG_UNKS) {
                            log.warn(skillIdStr + " is not a number.");
                        }
                        continue;
                    }
                    for (NXNode mainLevelNode : skillNode) {
                        String mainName = mainLevelNode.getName();
                        Object objValue = mainLevelNode.get();
                        String mainValue;
                        if(objValue != null){
                            mainValue = mainLevelNode.get().toString();
                        } else {
                            mainValue = "";
                        }
                        int intVal = -1337;
                        if (Util.isNumber(mainValue)) {
                            intVal = Integer.parseInt(mainValue);
                        }
                        switch (mainName) {
                            case "masterLevel":
                                skill.setMasterLevel(intVal);
                                break;
                            case "fixLevel":
                                skill.setFixLevel(intVal);
                                break;
                            case "invisible":
                                skill.setInvisible(intVal != 0);
                                break;
                            case "massSpell":
                                skill.setMassSpell(intVal != 0);
                                break;
                            case "type":
                                skill.setType(intVal);
                                break;
                            case "psd":
                                skill.setPsd(intVal != 0);
                                break;
                            case "psdSkill":
                                for (NXNode psdSkillNode : mainLevelNode) {
                                    skill.addPsdSkill(Integer.parseInt(psdSkillNode.getName()));
                                }
                                break;
                            case "elemAttr":
                                skill.setElemAttr(mainValue);
                                break;
                            case "hyper":
                                skill.setHyper(intVal);
                                break;
                            case "hyperStat":
                                skill.setHyperStat(intVal);
                                break;
                            case "vehicleID":
                                skill.setVehicleId(intVal);
                                break;
                            case "notCooltimeReset":
                                skill.setNotCooltimeReset(intVal != 0);
                                break;
                            case "notIncBuffDuration":
                                skill.setNotIncBuffDuration(intVal != 0);
                                break;
                            case "req":
                                for (NXNode reqChild : mainLevelNode) {
                                    String childName = reqChild.getName();
                                    String childValue = reqChild.get().toString();
                                    if ("reqTierPoint".equalsIgnoreCase(childName)) {
                                        skill.setReqTierPoint(Integer.parseInt(childValue));
                                    } else if (Util.isNumber(childName)) {
                                        skill.addReqSkill(
                                            Integer.parseInt(childName),
                                            Integer.parseInt(childValue)
                                        );
                                    }
                                }
                                break;
                            case "common":
                            case "info":
                            case "info2":
                                for (NXNode commonNode : mainLevelNode) {
                                    String nodeName = commonNode.getName();
                                    if (nodeName.equals("maxLevel")) {
                                        skill.setMaxLevel(Integer.parseInt(
                                            commonNode.get().toString()
                                        ));
                                    } else if (nodeName.contains("lt") && nodeName.length() <= 3) {
                                        NXNode rbNode = mainLevelNode.getChild(nodeName.replace("lt", "rb"));

                                        Point ltPoint = (Point)commonNode.get();
                                        Point rbPoint = (Point)rbNode.get();

                                        skill.addRect(
                                            new Rect(ltPoint, rbPoint)
                                        );
                                    } else {
                                        SkillStat skillStat = SkillStat.getSkillStatByString(nodeName);
                                        if (skillStat != null) {
                                            skill.addSkillStatInfo(
                                                skillStat,
                                                commonNode.get().toString()
                                            );
                                        } else if (!unkVals.contains(nodeName)) {
                                            if (LOG_UNKS) {
                                                log.warn("Unknown SkillStat " + nodeName);
                                            }
                                            unkVals.add(nodeName);
                                        }
                                    }
                                }
                                break;
                            case "addAttack":
                                for (NXNode addAttackNode : mainLevelNode) {
                                    String nodeName = addAttackNode.getName();
                                    //String nodeValue = (String)addAttackNode.get();
                                    switch (nodeName) {
                                        case "skillPlus":
                                            for (NXNode skillPlusNode : addAttackNode) {
                                                //String skillPlusNodeName = skillPlusNode.getName();
                                                String skillPlusNodeValue = skillPlusNode.get().toString();
                                                skill.addAddAttackSkills(
                                                    Integer.parseInt(skillPlusNodeValue)
                                                );
                                            }
                                            break;
                                    }
                                }
                                break;
                            case "extraSkillInfo":
                                for (NXNode extraSkillInfoNode : mainLevelNode) {
                                    int extraSkillDelay = 0;
                                    int extraSkillId = -1;
                                    for (NXNode extraSkillInfoIndividual : extraSkillInfoNode) {
                                        String extraSkillName = extraSkillInfoIndividual.getName();
                                        String extraSkillValue = extraSkillInfoIndividual.get().toString();
                                        switch (extraSkillName) {
                                            case "delay":
                                                extraSkillDelay = Integer.parseInt(extraSkillValue);
                                                break;
                                            case "skill":
                                                extraSkillId = Integer.parseInt(extraSkillValue);
                                                break;
                                            default:
                                                if (LOG_UNKS) {
                                                    log.warn(String.format(
                                                        "Unknown Extra Skill Info Name: %s",
                                                        extraSkillName
                                                    ));
                                                }
                                                break;
                                        }
                                    }
                                    if (extraSkillId > 0) {
                                        skill.addExtraSkillInfo(extraSkillId, extraSkillDelay);
                                    }
                                }
                                break;
                        }
                    }
                    skills.put(skillId, skill);
                }
            }
        }
    }

    public static Map<Integer, SkillInfo> getSkillInfos() {
        return skills;
    }

    public static SkillInfo getSkillInfoById(int skillId) {
        return getSkillInfos().get(skillId);
    }

    public static Skill getSkillDeepCopyById(int skillId) {
        SkillInfo si = getSkillInfoById(skillId);
        if (si == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setSkillId(si.getSkillId());
        skill.setRootId(si.getRootId());
        skill.setMasterLevel(si.getMaxLevel());
//      skill.setMasterLevel(si.getMasterLevel()); // for now, maybe always?
        skill.setMaxLevel(si.getMaxLevel());
        if (si.getMasterLevel() <= 0) {
            skill.setMasterLevel(skill.getMaxLevel());
        }
        skill.setCurrentLevel(Math.max(si.getFixLevel(), 0));
        return skill;
    }

    public static List<Skill> getSkillsByJob(short id) {
        return getSkillsByJob(id, false);
    }

    private static List<Skill> getSkillsByJob(short id, boolean rec) {
        List<Skill> res = new ArrayList<>();
        getSkillInfos().forEach((key, si) -> {
            if (si.getRootId() == id && !si.isInvisible()) {
                res.add(getSkillDeepCopyById(key));
            }
        });
        return res;
    }

    public static Map<Short, Map<Short, MobSkillInfo>> getMobSkillInfos() {
        return mobSkillInfos;
    }

    public static Map<Integer, MakingSkillRecipe> getMakingSkillRecipes() {
        return makingSkillRecipes;
    }

    public static MakingSkillRecipe getRecipeById(int recipeID) {
        return getMakingSkillRecipes().get(recipeID);
    }

    public static void addMobSkillInfo(MobSkillInfo msi) {
        getMobSkillInfos().computeIfAbsent(msi.getId(), k -> new HashMap<>());
        Map<Short, MobSkillInfo> msiLevelMap = getMobSkillInfos().get(msi.getId());
        msiLevelMap.put(msi.getLevel(), msi);
        getMobSkillInfos().put(msi.getId(), msiLevelMap);
    }

    public static void loadMobSkillsFromWz() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Skill.nx";
        NXNode mobSkills = new LazyNXFile(nxDir).resolve("MobSkill.img");
        Set<String> unks = new HashSet<>();

        for (NXNode mobSkill  : mobSkills) {
            short skillID = Short.parseShort(mobSkill.getName());
            for (NXNode levelNode : mobSkill.getChild("level")) {
                short level = Short.parseShort(levelNode.getName());
                MobSkillInfo msi = new MobSkillInfo();
                msi.setId(skillID);
                msi.setLevel(level);
                for (NXNode skillStatNode : levelNode) {
                    String name = skillStatNode.getName();
                    String value = skillStatNode.get() != null
                        ? skillStatNode.get().toString()
                        : "0"; // thius means null
                    String x = skillStatNode.hasChild("x")
                        ? skillStatNode.getChild("x").get().toString()
                        : "0"; // may look into it.
                    String y = skillStatNode.hasChild("y")
                        ? skillStatNode.getChild("y").get().toString()
                        : "0"; // means null

                    if (Util.isNumber(name)) {
                        msi.addIntToList(Integer.parseInt(value));
                        continue;
                    }
                    switch (name) {
                        case "x":
                            msi.putMobSkillStat(MobSkillStat.x, value);
                            break;
                        case "mpCon":
                            msi.putMobSkillStat(MobSkillStat.mpCon, value);
                            break;
                        case "interval":
                        case "inteval":
                            msi.putMobSkillStat(MobSkillStat.interval, value);
                            break;
                        case "hp":
                        case "HP":
                            msi.putMobSkillStat(MobSkillStat.hp, value);
                            break;
                        case "info":
                            msi.putMobSkillStat(MobSkillStat.info, value);
                            break;
                        case "y":
                            msi.putMobSkillStat(MobSkillStat.y, value);
                            break;
                        case "lt":
                            msi.setLt(new Position(Integer.parseInt(x), Integer.parseInt(y)));
                            break;
                        case "rb":
                            msi.setRb(new Position(Integer.parseInt(x), Integer.parseInt(y)));
                            break;
                        case "lt2":
                            msi.setLt2(new Position(Integer.parseInt(x), Integer.parseInt(y)));
                            break;
                        case "rb2":
                            msi.setRb2(new Position(Integer.parseInt(x), Integer.parseInt(y)));
                            break;
                        case "lt3":
                            msi.setLt3(new Position(Integer.parseInt(x), Integer.parseInt(y)));
                            break;
                        case "rb3":
                            msi.setRb3(new Position(Integer.parseInt(x), Integer.parseInt(y)));
                            break;
                        case "limit":
                            msi.putMobSkillStat(MobSkillStat.limit, value);
                            break;
                        case "broadCastScreenMsg":
                            msi.putMobSkillStat(MobSkillStat.broadCastScreenMsg, value);
                            break;
                        case "w":
                            msi.putMobSkillStat(MobSkillStat.w, value);
                            break;
                        case "z":
                            msi.putMobSkillStat(MobSkillStat.z, value);
                            break;
                        case "parsing":
                            msi.putMobSkillStat(MobSkillStat.parsing, value);
                            break;
                        case "prop":
                            msi.putMobSkillStat(MobSkillStat.prop, value);
                            break;
                        case "ignoreResist":
                            msi.putMobSkillStat(MobSkillStat.ignoreResist, value);
                            break;
                        case "count":
                            msi.putMobSkillStat(MobSkillStat.count, value);
                            break;
                        case "time":
                            msi.putMobSkillStat(MobSkillStat.time, value);
                            break;
                        case "targetAggro":
                            msi.putMobSkillStat(MobSkillStat.targetAggro, value);
                            break;
                        case "fieldScript":
                            msi.putMobSkillStat(MobSkillStat.fieldScript, value);
                            break;
                        case "elemAttr":
                            msi.putMobSkillStat(MobSkillStat.elemAttr, value);
                            break;
                        case "delay":
                            msi.putMobSkillStat(MobSkillStat.delay, value);
                            break;
                        case "rank":
                            msi.putMobSkillStat(MobSkillStat.rank, value);
                            break;
                        case "HPDeltaR":
                            msi.putMobSkillStat(MobSkillStat.HPDeltaR, value);
                            break;
                        case "summonEffect":
                            msi.putMobSkillStat(MobSkillStat.summonEffect, value);
                            break;
                        case "y2":
                            msi.putMobSkillStat(MobSkillStat.y2, value);
                            break;
                        case "q":
                            msi.putMobSkillStat(MobSkillStat.q, value);
                            break;
                        case "q2":
                            msi.putMobSkillStat(MobSkillStat.q2, value);
                            break;
                        case "s2":
                            msi.putMobSkillStat(MobSkillStat.s2, value);
                            break;
                        case "u":
                            msi.putMobSkillStat(MobSkillStat.u, value);
                            break;
                        case "u2":
                            msi.putMobSkillStat(MobSkillStat.u2, value);
                            break;
                        case "v":
                            msi.putMobSkillStat(MobSkillStat.v, value);
                            break;
                        case "z2":
                            msi.putMobSkillStat(MobSkillStat.z2, value);
                            break;
                        case "w2":
                            msi.putMobSkillStat(MobSkillStat.w2, value);
                            break;
                        case "skillAfter":
                            msi.putMobSkillStat(MobSkillStat.skillAfter, value);
                            break;
                        case "x2":
                            msi.putMobSkillStat(MobSkillStat.x2, value);
                            break;
                        case "script":
                            msi.putMobSkillStat(MobSkillStat.script, value);
                            break;
                        case "attackSuccessProp":
                            msi.putMobSkillStat(MobSkillStat.attackSuccessProp, value);
                            break;
                        case "bossHeal":
                            msi.putMobSkillStat(MobSkillStat.bossHeal, value);
                            break;
                        case "face":
                            msi.putMobSkillStat(MobSkillStat.face, value);
                            break;
                        case "callSkill":
                            msi.putMobSkillStat(MobSkillStat.callSkill, value);
                            break;
                        case "level":
                            msi.putMobSkillStat(MobSkillStat.level, value);
                            break;
                        case "linkHP":
                            msi.putMobSkillStat(MobSkillStat.linkHP, value);
                            break;
                        case "timeLimitedExchange":
                            msi.putMobSkillStat(MobSkillStat.timeLimitedExchange, value);
                            break;
                        case "summonDir":
                            msi.putMobSkillStat(MobSkillStat.summonDir, value);
                            break;
                        case "summonTerm":
                            msi.putMobSkillStat(MobSkillStat.summonTerm, value);
                            break;
                        case "castingTime":
                            msi.putMobSkillStat(MobSkillStat.castingTime, value);
                            break;
                        case "subTime":
                            msi.putMobSkillStat(MobSkillStat.subTime, value);
                            break;
                        case "reduceCasting":
                            msi.putMobSkillStat(MobSkillStat.reduceCasting, value);
                            break;
                        case "additionalTime":
                            msi.putMobSkillStat(MobSkillStat.additionalTime, value);
                            break;
                        case "force":
                            msi.putMobSkillStat(MobSkillStat.force, value);
                            break;
                        case "targetType":
                            msi.putMobSkillStat(MobSkillStat.targetType, value);
                            break;
                        case "forcex":
                            msi.putMobSkillStat(MobSkillStat.forcex, value);
                            break;
                        case "sideAttack":
                            msi.putMobSkillStat(MobSkillStat.sideAttack, value);
                            break;
                        case "afterEffect":
                        case "rangeGap":
                            msi.putMobSkillStat(MobSkillStat.rangeGap, value);
                            break;
                        case "noGravity":
                            msi.putMobSkillStat(MobSkillStat.noGravity, value);
                            break;
                        case "notDestroyByCollide":
                            msi.putMobSkillStat(MobSkillStat.notDestroyByCollide, value);
                            break;
                        case "effect":
                        case "mob":
                        case "mob0":
                        case "hit":
                        case "affected":
                        case "affectedOtherSkill":
                        case "crash":
                        case "effectToUser":
                        case "affected_after":
                        case "fixDamR":
                        case "limitMoveSkill":
                        case "tile":
                        case "footholdRect":
                        case "targetMobType":
                        case "areaWarning":
                        case "arType":
                        case "tremble":
                        case "otherSkill":
                        case "etcEffect":
                        case "etcEffect1":
                        case "etcEffect2":
                        case "etcEffect3":
                        case "bombInfo":
                        case "affected_pre":
                        case "fixDamR_BT":
                        case "affectedPhase":
                        case "screen":
                        case "notMissAttack":
                        case "ignoreEvasion":
                        case "fadeinfo":
                        case "randomTarget":
                        case "option_linkedMob":
                        case "affected0":
                        case "summonOnce":
                        case "head":
                        case "mobGroup":
                        case "exceptRange":
                        case "exchangeAttack":
                        case "range":
                        case "addDam":
                        case "special":
                        case "target":
                        case "fixedPos":
                        case "fixedDir":
                        case "i52":
                        case "start":
                        case "cancleType":
                        case "succeed":
                        case "failed":
                        case "during":
                        case "castingBarHide":
                        case "skillCancelAlways":
                        case "cancleDamage":
                        case "cancleDamageMultiplier":
                        case "bounceBall":
                        case "info2":
                        case "regen":
                        case "kockBackD":
                        case "areaSequenceDelay":
                        case "areaSequenceRandomSplit":
                        case "accelerationEffect":
                        case "repeatEffect":
                        case "brightness":
                        case "brightnessDuration":
                        case "success":
                        case "fail":
                        case "affected_S":
                        case "appear":
                        case "affected_XS":
                        case "disappear":
                        case "command":
                        case "damIncPos": // May be useful
                        case "option_poison": // ?
                        case "phaseUserCount": // I think this is done client side (users hit mapped to phase?)
                            break;
                        default:
                            if (!unks.contains(name)) {
                                if (LOG_UNKS) {
                                    log.warn(String.format("Unkown MobSkillStat %s with value %s (skill %d level %d)", name, value, skillID, level));
                                }
                                unks.add(name);
                            }
                            break;
                    }
                }
                addMobSkillInfo(msi);
            }
        }

    }

    private static MobSkillInfo getMobSkillInfoByIdAndLevel(short id, short level) {
        Map<Short, MobSkillInfo> innerMap = getMobSkillInfos().get(id);
        return innerMap.get(level);
    }

    public static MobSkillInfo getMobSkillInfoByIdAndLevel(int id, int level) {
        return getMobSkillInfoByIdAndLevel((short) id, (short) level);
    }


    public static void loadMakingRecipeSkillsFromWz() throws IOException {
        int[] recipes = {9200, 9201, 9202, 9203, 9204};
        for (Integer recipeCategory : recipes) {
            NXNode recipesRoot = new LazyNXFile(ServerConstants.NX_DIR + "/Skill.nx")
                .resolve(String.format("Recipe_%d.img",recipeCategory));

            if (recipesRoot == null){
                return;
            }

            for (NXNode recipeNode: recipesRoot) {
                MakingSkillRecipe msr = new MakingSkillRecipe();
                int recipeID = Integer.parseInt(recipeNode.getName());
                msr.setRecipeID(recipeID);
                msr.setReqSkillID(10000 * (recipeID / 10000));
                for (NXNode recipe : recipeNode) {
                    String name = recipe.getName();
                    Object valueObj = recipe.get();
                    switch (name) {
                        case "target":
                            for (NXNode targets : recipe) {
                                MakingSkillRecipe.TargetElem tar = new MakingSkillRecipe.TargetElem();
                                for (NXNode target : targets) {
                                    String targetName = target.getName();
                                    int targetValue = Integer.parseInt(target.get().toString());
                                    switch (targetName) {
                                        case "item":
                                            tar.setItemID(targetValue);
                                            break;
                                        case "count":
                                            tar.setCount(targetValue);
                                            break;
                                        case "probWeight":
                                            tar.setProbWeight(targetValue);
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn("Unknown target value " + targetName);
                                            }
                                            break;
                                    }
                                }
                                msr.addTarget(tar);
                            }
                            break;
                        case "weatherItem":
                            msr.setWeatherItemID(Integer.parseInt(valueObj.toString()));
                            break;
                        case "incSkillProficiency":
                            msr.setIncSkillProficiency(Integer.parseInt(valueObj.toString()));
                            break;
                        case "incSkillProficiencyOnFailure":
                            msr.setIncSkillProficiencyOnFailure(Integer.parseInt(valueObj.toString()));
                            break;
                        case "incSkillMasterProficiency":
                            msr.setIncSkillMasterProficiency(Integer.parseInt(valueObj.toString()));
                            break;
                        case "incSkillMasterProficiencyOnFailure":
                            msr.setIncSkillMasterProficiencyOnFailure(Integer.parseInt(valueObj.toString()));
                            break;
                        case "incFatigability":
                            msr.setIncFatigability(Integer.parseInt(valueObj.toString()));
                            break;
                        case "addedCoolProb":
                            msr.setAddedCoolProb(Integer.parseInt(valueObj.toString()));
                            break;
                        case "coolTimeSec":
                            msr.setCoolTimeSec(Integer.parseInt(valueObj.toString()));
                            break;
                        case "addedTimeTaken":
                            msr.setAddedSecForMaxGauge(Integer.parseInt(valueObj.toString()));
                            break;
                        case "period":
                            msr.setExpiredPeriod(Integer.parseInt(valueObj.toString()));
                            break;
                        case "premium":
                            msr.setPremiumItem(Integer.parseInt(valueObj.toString()) != 0);
                            break;
                        case "needOpenItem":
                            msr.setNeedOpenItem(Integer.parseInt(valueObj.toString()) != 0);
                            break;
                        case "reqSkillLevel":
                            msr.setRecommandedSkillLevel(Integer.parseInt(valueObj.toString()));
                            break;
                        case "reqSkillProficiency":
                            msr.setReqSkillProficiency(Integer.parseInt(valueObj.toString()));
                            break;
                        case "reqMeso":
                            msr.setReqMeso(Integer.parseInt(valueObj.toString()));
                            break;
                        case "reqMapObjectTag":
                            msr.setReqMapObjectTag(valueObj.toString());
                            break;
                        case "recipe":
                            for (NXNode ingredients : recipe) {
                                int itemID = -1, count = -1;
                                for (NXNode ingredient : ingredients) {
                                    String ingredientName = ingredient.getName();
                                    int ingredientValue = Integer.parseInt(ingredient.get().toString());
                                    switch (ingredientName) {
                                        case "item":
                                            itemID = ingredientValue;
                                            break;
                                        case "count":
                                            count = ingredientValue;
                                            break;
                                        default:
                                            if (LOG_UNKS) {
                                                log.warn("Unknown ingredient value " + ingredientName);
                                            }
                                            break;
                                    }
                                }
                                if (itemID != -1 && count != -1) {
                                    msr.addIngredient(itemID, count);
                                }
                            }
                            break;
                        default:
                            if (LOG_UNKS) {
                                log.warn("Unknown recipe value " + name);
                            }
                            break;
                    }
                }
                makingSkillRecipes.put(recipeID, msr);
            }
        }
    }

    public static void generateDatFiles() {
        log.info("Started generating skill data.");
        try {
            long start = System.currentTimeMillis();
            loadSkillsFromWz();
            log.info(String.format("Completed generating skill data in %dms.", System.currentTimeMillis() - start));
            log.info("Started generating mob skill data.");
            start = System.currentTimeMillis();
            loadMobSkillsFromWz();
            log.info(String.format("Completed generating mob skill data in %dms.", System.currentTimeMillis() - start));
            log.info("Started generating recipe skill data.");
            start = System.currentTimeMillis();
            loadMakingRecipeSkillsFromWz();
            log.info(String.format(
                "Completed generating recipe skill data in %dms.",
                System.currentTimeMillis() - start
            ));
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        generateDatFiles();
    }

    public static void clear() {
        getMobSkillInfos().clear();
    }

}
