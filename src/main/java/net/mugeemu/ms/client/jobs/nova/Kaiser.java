package net.mugeemu.ms.client.jobs.nova;

import net.mugeemu.ms.client.character.CharacterStat;
import net.mugeemu.ms.client.character.info.HitInfo;
import net.mugeemu.ms.client.character.items.Item;
import net.mugeemu.ms.client.character.skills.Option;
import net.mugeemu.ms.client.character.skills.Skill;
import net.mugeemu.ms.client.character.skills.SkillStat;
import net.mugeemu.ms.client.character.skills.StopForceAtom;
import net.mugeemu.ms.client.character.skills.info.AttackInfo;
import net.mugeemu.ms.client.character.skills.info.MobAttackInfo;
import net.mugeemu.ms.client.character.skills.info.SkillInfo;
import net.mugeemu.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.mugeemu.ms.client.character.skills.temp.TemporaryStatManager;
import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.constants.SkillConstants;
import net.mugeemu.ms.loaders.ItemData;
import net.mugeemu.ms.loaders.SkillData;
import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.jobs.Job;
import net.mugeemu.ms.enums.ChatType;
import net.mugeemu.ms.enums.MoveAbility;
import net.mugeemu.ms.life.AffectedArea;
import net.mugeemu.ms.life.Summon;
import net.mugeemu.ms.life.mob.Mob;
import net.mugeemu.ms.life.mob.MobStat;
import net.mugeemu.ms.life.mob.MobTemporaryStat;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.util.Rect;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.world.field.Field;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 12/14/2017.
 */
public class Kaiser extends Job {

    public static final int VERTICAL_GRAPPLE = 60001218;
    public static final int TRANSFIGURATION = 60000219; //Morph Gauge (SmashStack)
    public static final int DRAGON_LINK = 60001225;

    public static final int TEMPEST_BLADES_THREE = 61101002;
    public static final int TEMPEST_BLADES_THREE_FF = 61110211;
    public static final int BLAZE_ON = 61101004; //Buff

    public static final int FINAL_FORM_THIRD = 61111008; //Buff 3rd Job
    public static final int STONE_DRAGON = 61111002; //Summon (Speed Debuff)
    public static final int STONE_DRAGON_FINAL_FORM = 61111220; //Summon (Speed Debuff)
    public static final int CURSEBITE = 61111003; //Buff

    public static final int FINAL_FORM_FOURTH = 61120008; //Buff 4rd Job
    public static final int TEMPEST_BLADES_FIVE = 61120007;
    public static final int TEMPEST_BLADES_FIVE_FF = 61121217;
    public static final int GRAND_ARMOR = 61121009; //Buff
    public static final int NOVA_WARRIOR_KAISER = 61121014; //Buff
    public static final int NOVA_TEMPERANCE_KAISER = 61121015;

    public static final int FINAL_TRANCE = 61121053;
    public static final int KAISERS_MAJESTY = 61121054;


    //Attacking Skills
    public static final int DRAGON_SLASH_1 = 61001000; //First Swing
    public static final int DRAGON_SLASH_2 = 61001004; //2nd Swing
    public static final int DRAGON_SLASH_3 = 61001005; //Last Swing
    public static final int DRAGON_SLASH_1_FINAL_FORM = 61120219; //Swing Final Form

    public static final int FLAME_SURGE = 61001101;
    public static final int FLAME_SURGE_FINAL_FORM = 61111215;

    public static final int IMPACT_WAVE = 61101100;
    public static final int IMPACT_WAVE_FINAL_FORM = 61111216;
    public static final int PIERCING_BLAZE = 61101101; //Special Attack (Stun Debuff)
    public static final int PIERCING_BLAZE_FINAL_FORM = 61111217;

    public static final int WING_BEAT = 61111100; //Special Attack (Speed Debuff)
    public static final int WING_BEAT_FINAL_FORM = 61111111;
    public static final int PRESSURE_CHAIN = 61111101; //Special Attack (Stun Debuff)
    public static final int PRESSURE_CHAIN_FINAL_FORM = 61111219;

    public static final int GIGA_WAVE = 61121100; //Special Attack (Speed Debuff)
    public static final int GIGA_WAVE_FINAL_FORM = 61121201;
    public static final int INFERNO_BREATH = 61121105;
    public static final int INFERNO_BREATH_FINAL_FORM = 61121222;
    public static final int DRAGON_BARRAGE = 61121102;
    public static final int DRAGON_BARRAGE_FINAL_FORM = 61121203;
    public static final int BLADE_BURST = 61121104;
    public static final int BLADE_BURST_FINAL_FORM = 61121221;


    //Realign Skills
    public static final int REALIGN_ATTACKER_MODE = 60001217; //Unlimited Duration
    public static final int REALIGN_DEFENDER_MODE = 60001216; //Unlimited Duration

    public static final int REALIGN_ATTACKER_MODE_I = 61100008;
    public static final int REALIGN_DEFENDER_MODE_I = 61100005;

    public static final int REALIGN_ATTACKER_MODE_II = 61110010;
    public static final int REALIGN_DEFENDER_MODE_II = 61110005;

    public static final int REALIGN_ATTACKER_MODE_III = 61120013;
    public static final int REALIGN_DEFENDER_MODE_III = 61120010;

    private final int[] addedSkills = new int[]{
            REALIGN_ATTACKER_MODE,
            REALIGN_DEFENDER_MODE,
            VERTICAL_GRAPPLE,
            TRANSFIGURATION,
            DRAGON_LINK,
    };

    private final int[] buffs = new int[]{
            REALIGN_ATTACKER_MODE,
            REALIGN_DEFENDER_MODE,
            TEMPEST_BLADES_THREE,
            TEMPEST_BLADES_THREE_FF,
            BLAZE_ON,
            FINAL_FORM_THIRD,
            STONE_DRAGON,
            STONE_DRAGON_FINAL_FORM,
            CURSEBITE,
            FINAL_FORM_FOURTH,
            TEMPEST_BLADES_FIVE,
            TEMPEST_BLADES_FIVE_FF,
            GRAND_ARMOR,
            NOVA_WARRIOR_KAISER,
            FINAL_TRANCE,
            KAISERS_MAJESTY,
    };

    public Kaiser(Char chr) {
        super(chr);
        if (chr.getId() != 0 && isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isKaiser(id);
    }

    // Buff related methods --------------------------------------------------------------------------------------------

    public void handleBuff(Client c, InPacket inPacket, int skillID, byte slv) {
        Char chr = c.getChr();
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        TemporaryStatManager tsm = c.getChr().getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        Option o6 = new Option();
        Summon summon;
        Field field;
        Item item = chr.getEquippedInventory().getItemBySlot((short) 11);
        StopForceAtom stopForceAtom = new StopForceAtom();
        int weaponID = item.getItemId();
        switch (skillID) {
            case REALIGN_ATTACKER_MODE:
                if (tsm.hasStatBySkillId(skillID)) {
                    tsm.removeStatsBySkill(skillID);
                } else {
                    tsm.removeStatsBySkill(REALIGN_DEFENDER_MODE);
                    giveRealignAttackBuffs();
                }
                break;
            case REALIGN_DEFENDER_MODE:
                if (tsm.hasStatBySkillId(skillID)) {
                    tsm.removeStatsBySkill(skillID);
                } else {
                    tsm.removeStatsBySkill(REALIGN_ATTACKER_MODE);
                    giveRealignDefendBuffs();
                }
                break;
            case BLAZE_ON:
                o1.nOption = si.getValue(SkillStat.x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Booster, o1);
                break;
            case CURSEBITE:
                o1.nOption = si.getValue(SkillStat.asrR, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.AsrR, o1);
                o2.nOption = si.getValue(SkillStat.terR, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.TerR, o2);
                break;
            case GRAND_ARMOR:
                // w = party dmg taken  v = self dmg taken
                o1.nOption = si.getValue(SkillStat.v, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.DamageReduce, o1);
                break;
            case NOVA_WARRIOR_KAISER:
                o1.nReason = skillID;
                o1.nValue = si.getValue(SkillStat.x, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndieStatR, o1);
                break;
            case TEMPEST_BLADES_THREE:
                if (tsm.getOption(CharacterTemporaryStat.StopForceAtomInfo).nOption != 1 && tsm.hasStat(
                    CharacterTemporaryStat.StopForceAtomInfo)) {
                    tsm.removeStat(CharacterTemporaryStat.StopForceAtomInfo, true);
                    tsm.sendResetStatPacket();
                }
                o1.nOption = 1;
                o1.rOption = skillID;
                List<Integer> angles = Arrays.asList(0, 0, 0);
                stopForceAtom.setCount(3);
                stopForceAtom.setIdx(1);
                stopForceAtom.setWeaponId(weaponID);
                stopForceAtom.setAngleInfo(angles);
                tsm.setStopForceAtom(stopForceAtom);
                tsm.putCharacterStatValue(CharacterTemporaryStat.StopForceAtomInfo, o1);
                break;
            case TEMPEST_BLADES_THREE_FF: //Final Form
                if (tsm.getOption(CharacterTemporaryStat.StopForceAtomInfo).nOption != 3 && tsm.hasStat(
                    CharacterTemporaryStat.StopForceAtomInfo)) {
                    tsm.removeStat(CharacterTemporaryStat.StopForceAtomInfo, true);
                    tsm.sendResetStatPacket();
                }
                o1.nOption = 3;
                o1.rOption = skillID;
                angles = Arrays.asList(0, 0, 0);
                stopForceAtom.setCount(3);
                stopForceAtom.setIdx(3);
                stopForceAtom.setWeaponId(weaponID);
                stopForceAtom.setAngleInfo(angles);
                tsm.setStopForceAtom(stopForceAtom);
                tsm.putCharacterStatValue(CharacterTemporaryStat.StopForceAtomInfo, o1);
                break;
            case TEMPEST_BLADES_FIVE:
                if (tsm.getOption(CharacterTemporaryStat.StopForceAtomInfo).nOption != 2 && tsm.hasStat(
                    CharacterTemporaryStat.StopForceAtomInfo)) {
                    tsm.removeStat(CharacterTemporaryStat.StopForceAtomInfo, true);
                    tsm.sendResetStatPacket();
                }
                o1.nOption = 2;
                o1.rOption = skillID;
                angles = Arrays.asList(0, 0, 0, 0, 0);
                stopForceAtom.setCount(5);
                stopForceAtom.setIdx(2);
                stopForceAtom.setWeaponId(weaponID);
                stopForceAtom.setAngleInfo(angles);
                tsm.setStopForceAtom(stopForceAtom);
                tsm.putCharacterStatValue(CharacterTemporaryStat.StopForceAtomInfo, o1);
                break;
            case TEMPEST_BLADES_FIVE_FF: //Final Form
                if (tsm.getOption(CharacterTemporaryStat.StopForceAtomInfo).nOption != 4 && tsm.hasStat(
                    CharacterTemporaryStat.StopForceAtomInfo)) {
                    tsm.removeStat(CharacterTemporaryStat.StopForceAtomInfo, true);
                    tsm.sendResetStatPacket();
                }
                o1.nOption = 4;
                o1.rOption = skillID;
                angles = Arrays.asList(0, 0, 0, 0, 0);
                stopForceAtom.setCount(5);
                stopForceAtom.setIdx(4);
                stopForceAtom.setWeaponId(weaponID);
                stopForceAtom.setAngleInfo(angles);
                tsm.setStopForceAtom(stopForceAtom);
                tsm.putCharacterStatValue(CharacterTemporaryStat.StopForceAtomInfo, o1);
                break;
            case FINAL_FORM_THIRD:
                if (tsm.hasStat(CharacterTemporaryStat.StopForceAtomInfo)) {
                    tsm.removeStat(CharacterTemporaryStat.StopForceAtomInfo, true);
                    tsm.sendResetStatPacket();
                }
                o6.nOption = 1200;
                o6.rOption = skillID;
                o6.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Morph, o6);
                o1.nOption = si.getValue(SkillStat.cr, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.CriticalBuff, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(SkillStat.indiePMdR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePMdR, o2);
                o3.nOption = si.getValue(SkillStat.jump, slv);
                o3.rOption = skillID;
                o3.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Jump, o3);
                o4.nOption = si.getValue(SkillStat.prop, slv);
                o4.rOption = skillID;
                o4.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Stance, o4);
                o5.nOption = si.getValue(SkillStat.speed, slv);
                o5.rOption = skillID;
                o5.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Speed, o5);
                resetGauge(c, tsm);
                break;
            case FINAL_TRANCE:
            case FINAL_FORM_FOURTH:
                if (tsm.hasStat(CharacterTemporaryStat.StopForceAtomInfo)) {
                    tsm.removeStat(CharacterTemporaryStat.StopForceAtomInfo, true);
                    tsm.sendResetStatPacket();
                }
                o6.nOption = 1201;
                o6.rOption = skillID;
                o6.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Morph, o6);
                o1.nOption = si.getValue(SkillStat.cr, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.CriticalBuff, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(SkillStat.indiePMdR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePMdR, o2);
                o3.nOption = si.getValue(SkillStat.jump, slv);
                o3.rOption = skillID;
                o3.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Jump, o3);
                o4.nOption = si.getValue(SkillStat.prop, slv);
                o4.rOption = skillID;
                o4.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Stance, o4);
                o5.nOption = si.getValue(SkillStat.speed, slv);
                o5.rOption = skillID;
                o5.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Speed, o5);
                resetGauge(c, tsm);
                break;
            case KAISERS_MAJESTY:
                o1.nReason = skillID;
                o1.nValue = -1;
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBooster, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(SkillStat.indiePad, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePAD, o2);
                for (int skillId : chr.getSkillCoolTimes().keySet()) {
                    if (!SkillData.getSkillInfoById(skillId).isNotCooltimeReset() && SkillData.getSkillInfoById(skillId).getHyper() == 0) {
                        chr.resetSkillCoolTime(skillId);
                    }
                }
                break;
            case STONE_DRAGON:
            case STONE_DRAGON_FINAL_FORM:
                summon = Summon.getSummonBy(c.getChr(), skillID, slv);
                field = c.getChr().getField();
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 0);
                summon.setMoveAbility(MoveAbility.Stop);
                Position position = new Position(chr.isLeft() ? chr.getPosition().getX() - 250 : chr.getPosition().getX() + 250, chr.getPosition().getY());
                summon.setCurFoothold((short) chr.getField().findFootHoldBelow(position).getId());
                summon.setPosition(position);
                field.spawnSummon(summon);
                break;
        }
        tsm.sendSetStatPacket();
    }

    public boolean isBuff(int skillID) {
        return super.isBuff(skillID) || Arrays.stream(buffs).anyMatch(b -> b == skillID);
    }

    public void giveRealignAttackBuffs() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        int[] realignattacks = new int[]{
                REALIGN_ATTACKER_MODE,
                REALIGN_ATTACKER_MODE_I,
                REALIGN_ATTACKER_MODE_II,
                REALIGN_ATTACKER_MODE_III,
        };
        int zPadX = 0;
        int zCr = 0;
        int zBdR = 0;
        for (int realignattack : realignattacks) {
            if (chr.hasSkill(realignattack)) {
                Skill skill = chr.getSkill(realignattack);
                byte slv = (byte) skill.getCurrentLevel();
                SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
                zPadX += si.getValue(SkillStat.padX, slv);
                zCr += si.getValue(SkillStat.cr, slv);
                zBdR += si.getValue(SkillStat.bdR, slv);
            }
        }
        o1.nOption = zPadX;
        o1.rOption = REALIGN_ATTACKER_MODE;
        tsm.putCharacterStatValue(CharacterTemporaryStat.PAD, o1);
        o2.nOption = zCr;
        o2.rOption = REALIGN_ATTACKER_MODE;
        tsm.putCharacterStatValue(CharacterTemporaryStat.CriticalBuff, o2);
        o3.nOption = zBdR;
        o3.rOption = REALIGN_ATTACKER_MODE;
        tsm.putCharacterStatValue(CharacterTemporaryStat.HayatoBoss, o3);
        tsm.sendSetStatPacket();
    }

    public void giveRealignDefendBuffs() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        int[] realigndefends = new int[]{
                REALIGN_DEFENDER_MODE,
                REALIGN_DEFENDER_MODE_I,
                REALIGN_DEFENDER_MODE_II,
                REALIGN_DEFENDER_MODE_III,
        };
        int zDef = 0;
        int zAcc = 0;
        int zMHPR = 0;
        for (int realigndefend : realigndefends) {
            if (chr.hasSkill(realigndefend)) {
                Skill skill = chr.getSkill(realigndefend);
                byte slv = (byte) skill.getCurrentLevel();
                SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
                zDef += si.getValue(SkillStat.pddX, slv);
                zAcc += si.getValue(SkillStat.accX, slv);
                zMHPR += si.getValue(SkillStat.mhpR, slv);
            }
        }
        o1.nOption = zDef;
        o1.rOption = REALIGN_DEFENDER_MODE;
        tsm.putCharacterStatValue(CharacterTemporaryStat.PDD, o1);
        tsm.putCharacterStatValue(CharacterTemporaryStat.MDD, o1);
        o2.nOption = zAcc;
        o2.rOption = REALIGN_DEFENDER_MODE;
        tsm.putCharacterStatValue(CharacterTemporaryStat.ACC, o2);
        o3.nOption = zMHPR;
        o3.rOption = REALIGN_DEFENDER_MODE;
        tsm.putCharacterStatValue(CharacterTemporaryStat.HayatoHPR, o3);
        tsm.sendSetStatPacket();
    }

    public static int getTempBladeSkill(Char chr, TemporaryStatManager tsm) {
        int skill = 0;
        if (chr.hasSkill(TEMPEST_BLADES_THREE)) {
            skill = TEMPEST_BLADES_THREE;
        }
        if (chr.hasSkill(TEMPEST_BLADES_THREE) && tsm.hasStat(CharacterTemporaryStat.Morph)) {
            skill = TEMPEST_BLADES_THREE_FF;
        }
        if (chr.hasSkill(TEMPEST_BLADES_FIVE)) {
            skill = TEMPEST_BLADES_FIVE;
        }
        if (chr.hasSkill(TEMPEST_BLADES_FIVE) && tsm.hasStat(CharacterTemporaryStat.Morph)) {
            skill = TEMPEST_BLADES_FIVE_FF;
        }
        return skill;
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        if (hasHitMobs) {
            incrementMorphGauge(tsm, (SkillConstants.getKaiserGaugeIncrementBySkill(attackInfo.skillId) * attackInfo.mobAttackInfo.size()));
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case PIERCING_BLAZE:
                SkillInfo pbi = SkillData.getSkillInfoById(PIERCING_BLAZE);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(pbi.getValue(SkillStat.prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        if (!mob.isBoss()) {
                            MobTemporaryStat mts = mob.getTemporaryStat();
                            o1.nOption = 1;
                            o1.rOption = skillID;
                            o1.tOption = pbi.getValue(SkillStat.time, slv);
                            mts.addStatOptionsAndBroadcast(MobStat.Stun, o1);
                        }
                    }
                }
                break;
            case PIERCING_BLAZE_FINAL_FORM:
                SkillInfo pbffi = SkillData.getSkillInfoById(PIERCING_BLAZE_FINAL_FORM);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    if (!mob.isBoss()) {
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skillID;
                        o1.tOption = pbffi.getValue(SkillStat.time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Stun, o1);
                    }
                }
                break;
            case WING_BEAT:
                SkillInfo wbi = SkillData.getSkillInfoById(WING_BEAT);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(wbi.getValue(SkillStat.prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skillID;
                        o1.tOption = 5;
                        mts.addStatOptionsAndBroadcast(MobStat.Speed, o1);
                    }
                }
                break;
            case WING_BEAT_FINAL_FORM:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    o1.nOption = 1;
                    o1.rOption = skillID;
                    o1.tOption = 5;
                    mts.addStatOptionsAndBroadcast(MobStat.Speed, o1);
                }
                break;
            case PRESSURE_CHAIN:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(SkillStat.prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        if (!mob.isBoss()) {
                            MobTemporaryStat mts = mob.getTemporaryStat();
                            o1.nOption = 1;
                            o1.rOption = skillID;
                            o1.tOption = 3;
                            mts.addStatOptionsAndBroadcast(MobStat.Stun, o1);
                        }
                    }
                }
                break;
            case PRESSURE_CHAIN_FINAL_FORM:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    if (!mob.isBoss()) {
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skillID;
                        o1.tOption = 3;
                        mts.addStatOptionsAndBroadcast(MobStat.Stun, o1);
                    }
                }
                break;
            case GIGA_WAVE:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(SkillStat.prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = -30;
                        o1.rOption = skillID;
                        o1.tOption = si.getValue(SkillStat.time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Speed, o1);
                    }
                }
                break;
            case GIGA_WAVE_FINAL_FORM:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    o1.nOption = -30;
                    o1.rOption = GIGA_WAVE;
                    o1.tOption = 4;
                    mts.addStatOptionsAndBroadcast(MobStat.Speed, o1);
                }
                break;
            case STONE_DRAGON:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(SkillStat.prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skillID;
                        o1.tOption = si.getValue(SkillStat.time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Speed, o1);
                    }
                }
                break;
            case STONE_DRAGON_FINAL_FORM:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(SkillStat.prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skillID;
                        o1.tOption = si.getValue(SkillStat.time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Speed, o1);
                    }
                }
                break;

            case INFERNO_BREATH:
            case INFERNO_BREATH_FINAL_FORM:
                SkillInfo rca = SkillData.getSkillInfoById(INFERNO_BREATH);
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (chr.getField().getAffectedAreas().stream()
                            .filter(aa -> aa.getSkillID() == INFERNO_BREATH && aa.getCharID() == chr.getId())
                            .collect(Collectors.toList()).size() > 3) {
                        continue; // to limit the amount of AAs
                    }

                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    AffectedArea aa = AffectedArea.getAffectedArea(chr, attackInfo);
                    aa.setDuration(rca.getValue(SkillStat.cooltime, slv) * 1000);
                    aa.setMobOrigin((byte) 1);
                    aa.setSkillID(INFERNO_BREATH);
                    aa.setPosition(mob.getPosition());
                    Rect rect = rca.getRects().get(0);
                    if (!attackInfo.left) {
                        rect = rect.moveRight();
                    }
                    aa.setRect(aa.getPosition().getRectAround(rect));
                    aa.setDelay((short) 7); //spawn delay
                    chr.getField().spawnAffectedArea(aa);
                }
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    private void incrementMorphGauge(TemporaryStatManager tsm, int increment) {
        Option o = new Option();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        SkillInfo gaugeInfo = SkillData.getSkillInfoById(60000219);
        int amount = 1;
        int stage = 0;
        if (chr.hasSkill(60000219)) {
            amount = tsm.getOption(CharacterTemporaryStat.SmashStack).nOption;
            if (amount <= (getKaiserGauge(chr))) {
                if (amount + increment > getKaiserGauge(chr)) {
                    amount = getKaiserGauge(chr);
                } else {
                    amount = tsm.getOption(CharacterTemporaryStat.SmashStack).nOption + increment;
                }
            }
            if (amount >= gaugeInfo.getValue(SkillStat.s, 1)) {
                stage = 1;
            }
            if (amount >= (gaugeInfo.getValue(SkillStat.v, 1)) - 1) {
                stage = 2;
            }
        }
        o.nOption = amount;
        o.rOption = 0;
        o.tOption = 0;
        tsm.putCharacterStatValue(CharacterTemporaryStat.SmashStack, o);
        o1.nOption = (stage * gaugeInfo.getValue(SkillStat.prop, 1));
        o1.rOption = 0;
        o1.tOption = 0;
        tsm.putCharacterStatValue(CharacterTemporaryStat.Stance, o1);
        o2.nOption = (stage * gaugeInfo.getValue(SkillStat.psdJump, 1));
        o2.rOption = 0;
        o2.tOption = 0;
        tsm.putCharacterStatValue(CharacterTemporaryStat.Jump, o2);
        o3.nOption = (stage * gaugeInfo.getValue(SkillStat.psdSpeed, 1));
        o3.rOption = 0;
        o3.tOption = 0;
        tsm.putCharacterStatValue(CharacterTemporaryStat.Speed, o3);
        o4.nReason = 0;
        o4.nValue = (stage * gaugeInfo.getValue(SkillStat.actionSpeed, 1));
        o4.tStart = (int) System.currentTimeMillis();
        o4.tTerm = 0;
        tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBooster, o4); //Indie
        tsm.sendSetStatPacket();
    }

    private void resetGauge(Client c, TemporaryStatManager tsm) {
        tsm.removeStat(CharacterTemporaryStat.SmashStack, false);
        tsm.sendResetStatPacket();
    }

    private int getKaiserGauge(Char chr) {
        int maxGauge;
        switch (chr.getJob()) {
            case 6100:
                maxGauge = SkillData.getSkillInfoById(60000219).getValue(SkillStat.s, 1);
                break;
            case 6110:
                maxGauge = SkillData.getSkillInfoById(60000219).getValue(SkillStat.u, 1);
                break;
            case 6111:
            case 6112:
                maxGauge = SkillData.getSkillInfoById(60000219).getValue(SkillStat.v, 1);
                break;
            default:
                maxGauge = 0;
        }
        return maxGauge;
    }

    @Override
    public int getFinalAttackSkill() {
        return 0;
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Client c, int skillID, byte slv, InPacket inPacket) {
        super.handleSkill(c, skillID, slv, inPacket);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Char chr = c.getChr();
        Skill skill = chr.getSkill(skillID);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillID);
        }
        chr.chatMessage(ChatType.Mob, "SkillID: " + skillID);
        if (isBuff(skillID)) {
            handleBuff(c, inPacket, skillID, slv);
        } else {
            Option o1 = new Option();
            Option o2 = new Option();
            Option o3 = new Option();
            switch (skillID) {
                case NOVA_TEMPERANCE_KAISER:
                    tsm.removeAllDebuffs();
                    break;
            }
        }
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {

        super.handleHit(c, inPacket, hitInfo);
    }

    // Character creation related methods ------------------------------------------------------------------------------

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        cs.setLevel(10);
        cs.setJob(JobConstants.JobEnum.KAISER1.getJobId());
        cs.setStr(49);
        cs.setHp(543);
        cs.setMaxHp(543);
        cs.setMp(213);
        cs.setMaxMp(213);
        cs.setPosMap(400000000);
        Item secondary = ItemData.getItemDeepCopy(1352500);
        secondary.setBagIndex(10);
        chr.getAvatarData().getAvatarLook().getHairEquips().add(secondary.getItemId());
        chr.setSpToCurrentJob(5);
        chr.getEquippedInventory().addItem(secondary);
    }

}