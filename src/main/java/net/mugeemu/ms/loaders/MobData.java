package net.mugeemu.ms.loaders;

import net.mugeemu.ms.constants.GameConstants;
import net.mugeemu.ms.client.character.skills.Option;
import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.life.drop.DropInfo;
import net.mugeemu.ms.life.mob.ForcedMobStat;
import net.mugeemu.ms.life.mob.Mob;
import net.mugeemu.ms.life.mob.skill.MobSkill;
import net.mugeemu.ms.life.mob.MobTemporaryStat;
import org.apache.log4j.LogManager;
import net.mugeemu.ms.util.Util;
import us.aaronweiss.pkgnx.LazyNXFile;
import us.aaronweiss.pkgnx.NXNode;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static net.mugeemu.ms.life.mob.MobStat.*;

/**
 * Created on 12/30/2017.
 */
public class MobData {
    private static final boolean LOG_UNKS = false;
    private static final org.apache.log4j.Logger log = LogManager.getRootLogger();

    private static Map<Integer, Mob> mobs = new HashMap<>();

    public static Map<Integer, Mob> getMobs() {
        return mobs;
    }

    public static void addMob(Mob mob) {
        getMobs().put(mob.getTemplateId(), mob);
    }

    public static void generateDatFiles() {
        try {
            log.info("Started generating mob data.");
            long start = System.currentTimeMillis();
            loadMobsFromWz();
            QuestData.linkMobData();
            //saveToFile(ServerConstants.DAT_DIR + "/mobs");
            log.info(String.format("Completed generating mob data in %dms.", System.currentTimeMillis() - start));
        } catch (IOException e) {
            log.error("There was an error reading mob.nx :(");
            System.exit(-1);
        }
    }

    public static Mob getMobById(int id) {
        Mob mob = getMobs().get(id);
        if (mob == null) {
            mob = loadMobFromFile(id);
        }
        return mob;
    }

    public static Mob getMobDeepCopyById(int id) {
        Mob from = getMobById(id);
        Mob copy = null;
        if (from != null) {
            copy = from.deepCopy();
        }
        return copy;
    }

    private static Mob loadMobFromFile(int id) {
        File file = new File(ServerConstants.DAT_DIR + "/mobs/" + id + ".dat");
        if (!file.exists()) {
            return null;
        }
        Mob mob = null;
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            mob = new Mob(dataInputStream.readInt());
            ForcedMobStat fms = mob.getForcedMobStat();
            fms.setLevel(dataInputStream.readInt());
            mob.setFirstAttack(dataInputStream.readInt());
            Option bodyOpt = new Option();
            bodyOpt.nOption = dataInputStream.readInt();
//            mts.addStatOptions(BodyAttack, bodyOpt);
            fms.setMaxHP(dataInputStream.readLong());
            fms.setMaxMP(dataInputStream.readLong());
            fms.setPad(dataInputStream.readInt());
            fms.setPdr(dataInputStream.readInt());
            fms.setMad(dataInputStream.readInt());
            fms.setMdr(dataInputStream.readInt());
            fms.setAcc(dataInputStream.readInt());
            fms.setEva(dataInputStream.readInt());
            fms.setPushed(dataInputStream.readInt());
            fms.setExp(dataInputStream.readLong());
            mob.setSummonType(dataInputStream.readInt());
            mob.setCategory(dataInputStream.readInt());
            mob.setMobType(dataInputStream.readUTF());
            mob.setLink(dataInputStream.readInt());
            fms.setSpeed(dataInputStream.readInt());
            mob.setFs(dataInputStream.readDouble());
            mob.setElemAttr(dataInputStream.readUTF());
            mob.setHpTagColor(dataInputStream.readInt());
            mob.setHpTagBgcolor(dataInputStream.readInt());

            mob.setRespawnDelay(dataInputStream.readLong());

            mob.setHPgaugeHide(dataInputStream.readBoolean());
            mob.setBoss(dataInputStream.readBoolean());
            mob.setUndead(dataInputStream.readBoolean());
            mob.setNoRegen(dataInputStream.readBoolean());
            mob.setInvincible(dataInputStream.readBoolean());
            mob.setHideName(dataInputStream.readBoolean());
            mob.setHideHP(dataInputStream.readBoolean());
            mob.setChangeable(dataInputStream.readBoolean());
            mob.setNoFlip(dataInputStream.readBoolean());
            mob.setTower(dataInputStream.readBoolean());
            mob.setPartyBonusMob(dataInputStream.readBoolean());
            mob.setUseReaction(dataInputStream.readBoolean());
            mob.setPublicReward(dataInputStream.readBoolean());
            mob.setMinion(dataInputStream.readBoolean());
            mob.setForward(dataInputStream.readBoolean());
            mob.setRemoteRange(dataInputStream.readBoolean());
            mob.setIgnoreFieldOut(dataInputStream.readBoolean());
            mob.setIgnoreMoveImpact(dataInputStream.readBoolean());
            mob.setSkeleton(dataInputStream.readBoolean());
            mob.setHideUserDamage(dataInputStream.readBoolean());
            mob.setIndividualReward(dataInputStream.readBoolean());
            mob.setNotConsideredFieldSet(dataInputStream.readBoolean());
            mob.setNoDoom(dataInputStream.readBoolean());
            mob.setUseCreateScript(dataInputStream.readBoolean());
            mob.setBlockUserMove(dataInputStream.readBoolean());
            mob.setKnockback(dataInputStream.readBoolean());
            mob.setRemoveQuest(dataInputStream.readBoolean());
            mob.setOnFieldSetSummon(dataInputStream.readBoolean());
            mob.setUserControll(dataInputStream.readBoolean());
            mob.setNoDebuff(dataInputStream.readBoolean());
            mob.setTargetFromSvr(dataInputStream.readBoolean());
            mob.setChangeableMobType(dataInputStream.readUTF());
            mob.setRareItemDropLevel(dataInputStream.readInt());
            mob.setHpRecovery(dataInputStream.readInt());
            mob.setMpRecovery(dataInputStream.readInt());
            mob.setMbookID(dataInputStream.readInt());
            mob.setChaseSpeed(dataInputStream.readInt());
            mob.setExplosiveReward(dataInputStream.readInt());
            mob.setCharismaEXP(dataInputStream.readInt());
            mob.setFlySpeed(dataInputStream.readInt());
            mob.setWp(dataInputStream.readInt());
            mob.setSummonEffect(dataInputStream.readInt());
            mob.setFixedDamage(dataInputStream.readInt());
            mob.setRemoveAfter(dataInputStream.readInt());
            mob.setBodyDisease(dataInputStream.readInt());
            mob.setBodyDiseaseLevel(dataInputStream.readInt());
            mob.setPoint(dataInputStream.readInt());
            mob.setPartyBonusR(dataInputStream.readInt());
            mob.setPassiveDisease(dataInputStream.readInt());
            mob.setCoolDamage(dataInputStream.readInt());
            mob.setCoolDamageProb(dataInputStream.readInt());
            mob.setDamageRecordQuest(dataInputStream.readInt());
            mob.setSealedCooltime(dataInputStream.readInt());
            mob.setWillEXP(dataInputStream.readInt());
            mob.setFixedMoveDir(dataInputStream.readUTF());
            mob.setEscortMob(dataInputStream.readBoolean());
            boolean banMap = dataInputStream.readBoolean();
            if (banMap) {
                mob.setBanMap(true);
                mob.setBanType(dataInputStream.readInt());
                mob.setBanMsgType(dataInputStream.readInt());
                mob.setBanMsg(dataInputStream.readUTF());
                short size = dataInputStream.readShort();
                for (int i = 0; i < size; i++) {
                    mob.addBanMap(dataInputStream.readInt(), dataInputStream.readUTF());
                }
            }
            boolean patrolMob = dataInputStream.readBoolean();
            if (patrolMob) {
                mob.setPatrolMob(true);
                mob.setRange(dataInputStream.readInt());
                mob.setDetectX(dataInputStream.readInt());
                mob.setSenseX(dataInputStream.readInt());
            }
            short size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                mob.addQuest(dataInputStream.readInt());
            }
            size = dataInputStream.readShort();
            for (int i = 0; i < size; i++) {
                mob.addRevive(dataInputStream.readInt());
            }
            short skillSize = dataInputStream.readShort();
            short attackSize = dataInputStream.readShort();
            for (int i = 0; i < skillSize + attackSize; i++) {
                MobSkill ms = new MobSkill();
                ms.setSkillSN(dataInputStream.readInt());
                ms.setSkillID(dataInputStream.readInt());
                ms.setAction(dataInputStream.readByte());
                ms.setLevel(dataInputStream.readInt());
                ms.setEffectAfter(dataInputStream.readInt());
                ms.setSkillAfter(dataInputStream.readInt());
                ms.setPriority(dataInputStream.readByte());
                ms.setOnlyFsm(dataInputStream.readBoolean());
                ms.setOnlyOtherSkill(dataInputStream.readBoolean());
                ms.setDoFirst(dataInputStream.readBoolean());
                ms.setAfterDead(dataInputStream.readBoolean());
                ms.setSkillForbid(dataInputStream.readInt());
                ms.setAfterAttack(dataInputStream.readInt());
                ms.setAfterAttackCount(dataInputStream.readInt());
                ms.setAfterDelay(dataInputStream.readInt());
                ms.setFixDamR(dataInputStream.readInt());
                ms.setPreSkillIndex(dataInputStream.readInt());
                ms.setPreSkillCount(dataInputStream.readInt());
                ms.setCastTime(dataInputStream.readInt());
                ms.setCoolTime(dataInputStream.readInt());
                ms.setDelay(dataInputStream.readInt());
                ms.setUseLimit(dataInputStream.readInt());
                ms.setInfo(dataInputStream.readUTF());
                ms.setText(dataInputStream.readUTF());
                ms.setSpeak(dataInputStream.readUTF());
                if (i < skillSize) {
                    mob.addSkill(ms);
                } else {
                    mob.addAttack(ms);
                }
            }

            Option pImmuneOpt = new Option();
            pImmuneOpt.nOption = dataInputStream.readInt();
//            mts.addStatOptions(PImmune, pImmuneOpt);

            mob.setAppearType((byte) -2); // new spawn
            mob.setAfterAttack(-1);
            mob.setCurrentAction(-1);
            mob.setEliteGrade(-1);
            mob.setMoveAction((byte) 5); // normal monster?
            mob.setHp(fms.getMaxHP());
            mob.setMaxHp(fms.getMaxHP());
            mob.setMp(fms.getMaxMP());
            mob.setMaxMp(fms.getMaxMP());
            mob.setDrops(DropData.getDropInfoByID(mob.getTemplateId()).stream().filter(dropInfo -> !dropInfo.getReactorDrop()).collect(Collectors.toSet()));
            mob.getDrops().add(new DropInfo(
                GameConstants.MAX_DROP_CHANCE,
                    GameConstants.MIN_MONEY_MULT * mob.getForcedMobStat().getLevel(),
                    GameConstants.MAX_MONEY_MULT * mob.getForcedMobStat().getLevel()
            ));
            for (DropInfo di : mob.getDrops()) {
                di.generateNextDrop();
            }
            addMob(mob);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mob;
    }


    public static void loadMobsFromWz() throws IOException {
        String nxDir = ServerConstants.NX_DIR + "/Mob.nx";
        NXNode mobs = new LazyNXFile(nxDir).getRoot();


        for (NXNode mob : mobs) {
            if (mob.getName().equals("ExcelExport") || mob.getName().equals("QuestCountGroup")) {
                continue;
            }

            long respawnDelay = 0;
            for(NXNode mobInfo : mob){
                // Death animation check
                if(mobInfo.getName().matches("^(die)")){
                    for(NXNode delayNode : mobInfo) {
                        // Delay Check
                        if(delayNode.hasChild("delay")) {
                            respawnDelay += Long.parseLong(
                                delayNode.getChild("delay").get().toString()
                            );
                        }
                    }
                } else if (mobInfo.getName().equals("info")) {
                    // Now rest of data.
                    int id = Integer.parseInt(mob.getName().replace(".img", ""));
                    Mob currentMob = new Mob(id);
                    ForcedMobStat fms = currentMob.getForcedMobStat();
                    MobTemporaryStat mts = currentMob.getTemporaryStat();

                    for (NXNode info : mobInfo) {
                        String name = info.getName();
                        String value = info.get().toString();

                        switch (name) {
                            case "level":
                            case "Level":
                                fms.setLevel(Integer.parseInt(value));
                                break;
                            case "firstAttack":
                            case "firstattack":
                                currentMob.setFirstAttack((int) Double.parseDouble(value));
                                break;
                            case "bodyAttack":
                            case "bodyattack": // ...
                                Option bodyOpt = new Option();
                                bodyOpt.nOption = Integer.parseInt(value);
                                mts.addStatOptions(BodyAttack, bodyOpt);
                                break;
                            case "maxHP":
                            case "finalmaxHP":
                                if (Util.isNumber(value)) {
                                    fms.setMaxHP(Long.parseLong(value));
                                } else {
                                    fms.setMaxHP(1337);
                                }
                                break;
                            case "maxMP":
                                fms.setMaxMP(Integer.parseInt(value));
                                break;
                            case "PADamage":
                                fms.setPad(Integer.parseInt(value));
                                break;
                            case "PDDamage":
                                //mts.addStatOptions(PDR, "nPDR", Integer.parseInt(value));
                                break;
                            case "PDRate":
                                fms.setPdr(Integer.parseInt(value));
                                break;
                            case "MADamage":
                                fms.setMad(Integer.parseInt(value));
                                break;
                            case "MDDamage":
                                //mts.addStatOptions(PDR, "nMDR", Integer.parseInt(value));
                                break;
                            case "MDRate":
                                fms.setMdr(Integer.parseInt(value));
                                break;
                            case "acc":
                                fms.setAcc(Integer.parseInt(value));
                                break;
                            case "eva":
                                fms.setEva(Integer.parseInt(value));
                                break;
                            case "pushed":
                                fms.setPushed(Integer.parseInt(value));
                                break;
                            case "exp":
                                fms.setExp(Integer.parseInt(value));
                                break;
                            case "summonType":
                                currentMob.setSummonType(Integer.parseInt(value));
                                break;
                            case "category":
                                currentMob.setCategory(Integer.parseInt(value));
                                break;
                            case "mobType":
                                currentMob.setMobType(value);
                                break;
                            case "link":
                                currentMob.setLink(Integer.parseInt(value));
                                break;
                            case "speed":
                            case "Speed":
                                fms.setSpeed(Integer.parseInt(value));
                                break;
                            case "fs":
                                currentMob.setFs(Double.parseDouble(value));
                                break;
                            case "elemAttr":
                                currentMob.setElemAttr(value);
                                break;
                            case "hpTagColor":
                                currentMob.setHpTagColor(Integer.parseInt(value));
                                break;
                            case "hpTagBgcolor":
                                currentMob.setHpTagBgcolor(Integer.parseInt(value));
                                break;
                            case "HPgaugeHide":
                                currentMob.setHPgaugeHide(Integer.parseInt(value) == 1);
                                break;
                            case "boss":
                                currentMob.setBoss(Integer.parseInt(value) == 1);
                                break;
                            case "undead":
                            case "Undead":
                                currentMob.setUndead(Integer.parseInt(value) == 1);
                                break;
                            case "noregen":
                                currentMob.setNoRegen(Integer.parseInt(value) == 1);
                                break;
                            case "invincible":
                            case "invincibe": // neckson pls
                                currentMob.setInvincible(Integer.parseInt(value) == 1);
                                break;
                            case "hideName":
                            case "hidename":
                                currentMob.setHideName(Integer.parseInt(value) == 1);
                                break;
                            case "hideHP":
                                currentMob.setHideHP(Integer.parseInt(value) == 1);
                                break;
                            case "changeableMob":
                                currentMob.setChangeable(Integer.parseInt(value) == 1);
                                break;
                            case "noFlip":
                                currentMob.setNoFlip(Integer.parseInt(value) == 1);
                                break;
                            case "tower":
                                currentMob.setTower(Integer.parseInt(value) == 1);
                                break;
                            case "partyBonusMob":
                                currentMob.setPartyBonusMob(Integer.parseInt(value) == 1);
                                break;
                            case "useReaction":
                                currentMob.setUseReaction(Integer.parseInt(value) == 1);
                                break;
                            case "publicReward":
                                currentMob.setPublicReward(Integer.parseInt(value) == 1);
                                break;
                            case "minion":
                                currentMob.setMinion(Integer.parseInt(value) == 1);
                                break;
                            case "forward":
                                currentMob.setForward(Integer.parseInt(value) == 1);
                                break;
                            case "isRemoteRange":
                                currentMob.setIsRemoteRange(Integer.parseInt(value) == 1);
                                break;
                            case "ignoreFieldOut":
                                currentMob.setIgnoreFieldOut(Integer.parseInt(value) == 1);
                                break;
                            case "ignoreMoveImpact":
                                currentMob.setIgnoreMoveImpact(Integer.parseInt(value) == 1);
                                break;
                            case "skeleton":
                                currentMob.setSkeleton(Integer.parseInt(value) == 1);
                                break;
                            case "hideUserDamage":
                                currentMob.setHideUserDamage(Integer.parseInt(value) == 1);
                                break;
                            case "individualReward":
                                currentMob.setIndividualReward(Integer.parseInt(value) == 1);
                                break;
                            case "notConsideredFieldSet":
                                currentMob.setNotConsideredFieldSet(Integer.parseInt(value) == 1);
                                break;
                            case "noDoom":
                                currentMob.setNoDoom(Integer.parseInt(value) == 1);
                                break;
                            case "useCreateScript":
                                currentMob.setUseCreateScript(Integer.parseInt(value) == 1);
                                break;
                            case "blockUserMove":
                                currentMob.setBlockUserMove(Integer.parseInt(value) == 1);
                                break;
                            case "knockback":
                                currentMob.setKnockback(Integer.parseInt(value) == 1);
                                break;
                            case "removeQuest":
                                currentMob.setRemoveQuest(Integer.parseInt(value) == 1);
                                break;
                            case "onFieldSetSummon":
                                currentMob.setOnFieldSetSummon(Integer.parseInt(value) == 1);
                                break;
                            case "userControll":
                                currentMob.setUserControll(Integer.parseInt(value) == 1);
                                break;
                            case "noDebuff":
                                currentMob.setNoDebuff(Integer.parseInt(value) == 1);
                                break;
                            case "targetFromSvr":
                                currentMob.setTargetFromSvr(Integer.parseInt(value) == 1);
                                break;
                            case "changeableMob_Type":
                                currentMob.setChangeableMobType(value);
                                break;
                            case "rareItemDropLevel":
                                currentMob.setRareItemDropLevel(Integer.parseInt(value));
                                break;
                            case "hpRecovery":
                                currentMob.setHpRecovery(Integer.parseInt(value));
                                break;
                            case "mpRecovery":
                                currentMob.setMpRecovery(Integer.parseInt(value));
                                break;
                            case "mbookID":
                                currentMob.setMbookID(Integer.parseInt(value));
                                break;
                            case "chaseSpeed":
                                currentMob.setChaseSpeed(Integer.parseInt(value));
                                break;
                            case "explosiveReward":
                                currentMob.setExplosiveReward(Integer.parseInt(value));
                                break;
                            case "charismaEXP":
                                currentMob.setCharismaEXP(Integer.parseInt(value));
                                break;
                            case "flyspeed":
                            case "flySpeed":
                            case "FlySpeed":
                                currentMob.setFlySpeed(Integer.parseInt(value));
                                break;
                            case "wp":
                                currentMob.setWp(Integer.parseInt(value));
                                break;
                            case "summonEffect":
                                currentMob.setSummonEffect(Integer.parseInt(value));
                                break;
                            case "fixedDamage":
                                currentMob.setFixedDamage(Integer.parseInt(value));
                                break;
                            case "removeAfter":
                                currentMob.setRemoveAfter(Integer.parseInt(value));
                                break;
                            case "bodyDisease":
                                currentMob.setBodyDisease(Integer.parseInt(value));
                                break;
                            case "bodyDiseaseLevel":
                                currentMob.setBodyDiseaseLevel(Integer.parseInt(value));
                                break;
                            case "point":
                                currentMob.setPoint(Integer.parseInt(value));
                                break;
                            case "partyBonusR":
                                currentMob.setPartyBonusR(Integer.parseInt(value));
                                break;
                            case "PassiveDisease":
                                currentMob.setPassiveDisease(Integer.parseInt(value));
                                break;
                            case "coolDamage":
                                currentMob.setCoolDamage(Integer.parseInt(value));
                                break;
                            case "coolDamageProb":
                                currentMob.setCoolDamageProb(Integer.parseInt(value));
                                break;
                            case "damageRecordQuest":
                                currentMob.setDamageRecordQuest(Integer.parseInt(value));
                                break;
                            case "sealedCooltime":
                                currentMob.setSealedCooltime(Integer.parseInt(value));
                                break;
                            case "willEXP":
                                currentMob.setWillEXP(Integer.parseInt(value));
                                break;
                            case "fixedMoveDir":
                                currentMob.setFixedMoveDir(value);
                                break;
                            case "PImmune":
                                Option immOpt = new Option();
                                immOpt.nOption = Integer.parseInt(value);
                                mts.addStatOptions(PImmune, immOpt);
                                break;
                            case "patrol":
                                currentMob.setPatrolMob(true);
                                for (NXNode patrolNode : info) {
                                    String patrolNodeName = patrolNode.getName();
                                    String patrolNodeValue = patrolNode.get().toString();
                                    switch (patrolNodeName) {
                                        case "range":
                                            currentMob.setRange(Integer.parseInt(patrolNodeValue));
                                            break;
                                        case "detectX":
                                            currentMob.setDetectX(Integer.parseInt(patrolNodeValue));
                                            break;
                                        case "senseX":
                                            currentMob.setSenseX(Integer.parseInt(patrolNodeValue));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                break;
                            case "ban":
                                currentMob.setBanMap(true);
                                for (NXNode banNode : info) {
                                    String banNodeName = banNode.getName();
                                    String banNodeValue = banNode.get().toString();
                                    switch (banNodeName) {
                                        case "banType":
                                            currentMob.setBanType(Integer.parseInt(banNodeValue));
                                            break;
                                        case "banMsgType":
                                            currentMob.setBanMsgType(Integer.parseInt(banNodeValue));
                                            break;
                                        case "banMsg":
                                            currentMob.setBanMsg(banNodeValue);
                                            break;
                                        case "banMap":
                                            for (NXNode banMaps : banNode) {
                                                int banFieldID = 0;
                                                String banPortal = "";
                                                for (NXNode banMap : banMaps) {
                                                    String banMapName = banMap.getName();
                                                    String banMapValue = banMap.get().toString();
                                                    switch (banMapName) {
                                                        case "field":
                                                            banFieldID = Integer.parseInt(banMapValue);
                                                            break;
                                                        case "portal":
                                                            banPortal = banMapValue;
                                                            break;
                                                        default:
                                                            if (LOG_UNKS) {
                                                                log.warn(String.format(
                                                                    "Unknown ban map node %s with value %s",
                                                                    banMapName,
                                                                    banMapValue
                                                                ));
                                                            }
                                                            break;
                                                    }
                                                    if (banFieldID != 0) {
                                                        currentMob.addBanMap(banFieldID, banPortal);
                                                    }
                                                }
                                            }
                                            break;
                                    }
                                }
                                break;
                            case "revive":
                                currentMob.setRespawnDelay(respawnDelay);
                                //log.debug("Adding spawn delay: " + respawnDelay);
                                for (NXNode reviveNode : info) {
                                    int reviveValue = Integer.parseInt(reviveNode.get().toString());
                                    currentMob.addRevive(reviveValue);
                                }
                                break;
                            case "skill":
                            case "attack":
                                boolean attack = "attack".equalsIgnoreCase(name);
                                for (NXNode skillIDNode : info) {
                                    if (!Util.isNumber(skillIDNode.getName())) {
                                        continue;
                                    }
                                    MobSkill mobSkill = new MobSkill();
                                    mobSkill.setSkillSN(
                                        Integer.parseInt(skillIDNode.getName())
                                    );
                                    for (NXNode skillInfoNode : skillIDNode) {
                                        String skillNodeName = skillInfoNode.getName();
                                        String skillNodeValue = skillInfoNode.get().toString();
                                        switch (skillNodeName) {
                                            case "skill":
                                                mobSkill.setSkillID(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "action":
                                                mobSkill.setAction(Byte.parseByte(skillNodeValue));
                                                break;
                                            case "level":
                                                mobSkill.setLevel(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "effectAfter":
                                                if (!skillNodeValue.isEmpty()) {
                                                    mobSkill.setEffectAfter(Integer.parseInt(skillNodeValue));
                                                }
                                                break;
                                            case "skillAfter":
                                                mobSkill.setSkillAfter(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "priority":
                                                mobSkill.setPriority(Byte.parseByte(skillNodeValue));
                                                break;
                                            case "onlyFsm":
                                                mobSkill.setOnlyFsm(Integer.parseInt(skillNodeValue) != 0);
                                                break;
                                            case "onlyOtherSkill":
                                                mobSkill.setOnlyOtherSkill(Integer.parseInt(skillNodeValue) != 0);
                                                break;
                                            case "doFirst":
                                                mobSkill.setDoFirst(Integer.parseInt(skillNodeValue) != 0);
                                                break;
                                            case "afterDead":
                                                mobSkill.setAfterDead(Integer.parseInt(skillNodeValue) != 0);
                                                break;
                                            case "skillForbid":
                                                mobSkill.setSkillForbid(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "afterAttack":
                                                mobSkill.setAfterAttack(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "afterAttackCount":
                                                mobSkill.setAfterAttackCount(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "afterDelay":
                                                mobSkill.setAfterDelay(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "fixDamR":
                                                mobSkill.setFixDamR(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "preSkillIndex":
                                                mobSkill.setPreSkillIndex(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "preSkillCount":
                                                mobSkill.setPreSkillCount(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "castTime":
                                                mobSkill.setCastTime(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "cooltime":
                                                mobSkill.setCoolTime(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "delay":
                                                mobSkill.setDelay(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "useLimit":
                                                mobSkill.setUseLimit(Integer.parseInt(skillNodeValue));
                                                break;
                                            case "info":
                                                mobSkill.setInfo(skillNodeValue);
                                                break;
                                            case "text":
                                                mobSkill.setText(skillNodeValue);
                                                break;
                                            case "speak":
                                                mobSkill.setSpeak(skillNodeValue);
                                                break;
                                            default:
                                                if (LOG_UNKS) {
                                                    log.warn(String.format(
                                                        "Unknown skill node %s with value %s",
                                                        skillNodeName,
                                                        skillNodeValue
                                                    ));
                                                }
                                        }
                                    }
                                    if (attack) {
                                        currentMob.addAttack(mobSkill);
                                    } else {
                                        currentMob.addSkill(mobSkill);
                                    }
                                }
                                break;
                            case "selfDestruction":
                                // TODO Maybe more info?
                                currentMob.setSelfDestruction(true);
                                break;
                            case "escort":
                                currentMob.setEscortMob(Integer.parseInt(value) != 0);
                                break;
                            case "speak":
                            case "thumbnail":
                            case "default":
                            case "defaultHP":
                            case "defaultMP":
                            case "passive":
                            case "firstAttackRange":
                            case "nonLevelCheckEVA":
                            case "nonLevelCheckACC":
                            case "changeImg":
                            case "showNotRemoteDam":
                            case "buff":
                            case "damagedBySelectedMob":
                            case "damagedByMob":
                            case "getCP":
                            case "loseItem":
                            case "0":
                            case "onlyNormalAttack":
                            case "notConsiderFieldSet":
                            case "overSpeed":
                            case "ignoreMovable":
                            case "jsonLoad":
                            case "fixedBodyAttackDamageR":
                            case "adjustLayerZ":
                            case "damagedBySelectedSkill":
                            case "option_damagedByMob":
                            case "bodyKnockBack":
                            case "straightMoveDir":
                            case "onlyHittedByCommonAttack":
                            case "invincibleAttack":
                            case "noChase":
                            case "notAttack":
                            case "alwaysInAffectedRect":
                            case "firstShowHPTag":
                            case "pointFPSMode":
                            case "11":
                            case "prevLinkMob":
                            case "option_skeleton":
                            case "lifePoint":
                            case "defenseMob":
                            case "forceChaseEscort":
                            case "damageModification":
                            case "randomFlyingBoss":
                            case "randomFlyingMob":
                            case "stalking":
                            case "minimap":
                            case "removeOnMiss":
                            case "fieldEffect":
                            case "onceReward":
                            case "onMobDeadQR":
                            case "peddlerMob":
                            case "peddlerDamR":
                            case "rewardSprinkle":
                            case "rewardSprinkleCount":
                            case "rewardSprinkleSpeed":
                            case "ignorMoveImpact":
                            case "dropItemPeriod":
                            case "hideMove":
                            case "atom":
                            case "smartPhase":
                            case "trans":
                            case "chaseEffect":
                            case "dualGauge":
                            case "noReturnByDead":
                            case "AngerGauge":
                            case "ChargeCount":
                            case "upperMostLayer":
                            case "cannotEvade":
                            case "phase":
                            case "doNotRemove":
                            case "healOnDestroy":
                            case "debuffobj":
                            case "obtacle":
                            case "mobZone":
                            case "weapon":
                            case "forcedFlip":
                            case "buffStack":
                            case "001":
                            case "002":
                            case "003":
                            case "004":
                            case "005":
                            case "006":
                            case "007":
                            case "008":
                            case "009":
                            case "010":
                            case "011":
                            case "onlySelectedSkill":
                            case "finalAdjustedDamageRate":
                            case "battlePvP":
                            case "mobJobCategory":
                            case "considerUserCount":
                            case "randomMob":
                            case "dieHeight":
                            case "dieHeightTime":
                            case "notChase":
                            case "fixedStat":
                            case "allyMob":
                            case "linkMob":
                            case "skelAniMixRate":
                            case "mobZoneObjID":
                            case "mobZoneObjType":
                            case "holdRange":
                            case "targetChaseTime":
                            case "copyCharacter":
                            case "disable":
                            case "underObject":
                            case "1582":
                            case "peddlerCount":
                            case "bodyAttackInfo":
                            case "mobZoneType":
                            case "largeDamageRecord":
                            case "considerUserCounter":
                            case "damageByObtacleAtom":
                            case "info":
                            case "cantPassByTeleport":
                            case "250000":
                            case "forward_direction":
                                break;
                            default:
                                if (LOG_UNKS) {
                                    log.warn(String.format("Unkown property %s with value %s.", name, value));
                                }
                        }
                        getMobs().put(currentMob.getTemplateId(), currentMob);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        generateDatFiles();
    }

    public static void clear() {
        getMobs().clear();
    }
}
