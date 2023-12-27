package net.mugeemu.ms.client.jobs.cygnus;

import net.mugeemu.ms.client.character.info.HitInfo;
import net.mugeemu.ms.client.character.skills.Option;
import net.mugeemu.ms.client.character.skills.Skill;
import net.mugeemu.ms.client.character.skills.SkillStat;
import net.mugeemu.ms.client.character.skills.info.AttackInfo;
import net.mugeemu.ms.client.character.skills.info.SkillInfo;
import net.mugeemu.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.mugeemu.ms.client.character.skills.temp.TemporaryStatBase;
import net.mugeemu.ms.client.character.skills.temp.TemporaryStatManager;
import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.loaders.SkillData;
import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.enums.ChatType;
import net.mugeemu.ms.enums.TSIndex;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.world.field.Field;

import java.util.Arrays;

/**
 * Created on 12/14/2017.
 */
public class ThunderBreaker extends Noblesse {

    public static final int IMPERIAL_RECALL = 10001245;
    public static final int ELEMENTAL_EXPERT = 10000250;
    public static final int ELEMENTAL_SLASH = 10001244;
    public static final int NOBLE_MIND = 10000202;
    public static final int ELEMENTAL_SHIFT = 10001254;
    public static final int ELEMENTAL_SHIFT2 = 10000252;
    public static final int ELEMENTAL_HARMONY_STR = 10000246;

    public static final int LIGHTNING_ELEMENTAL = 15001022; //Buff (Charge) //Stackable Charge
    public static final int ELECTRIFIED = 15000023;

    public static final int KNUCKLE_BOOSTER = 15101022; //Buff
    public static final int LIGHTNING_BOOST = 15100025;

    public static final int GALE = 15111022; //Special Attack (Charge)
    public static final int LINK_MASTERY = 15110025; //Special Passive
    public static final int LIGHTNING_LORD = 15110026;

    public static final int ARC_CHARGER = 15121004; //Buff
    public static final int SPEED_INFUSION = 15121005; //Buff
    public static final int CALL_OF_CYGNUS_TB = 15121000; //Buff
    public static final int TYPHOON = 15120003;
    public static final int THUNDER_GOD = 15120008;

    public static final int GLORY_OF_THE_GUARDIANS_TB = 15121053;
    public static final int PRIMAL_BOLT = 15121054;

    private int[] addedSkills = new int[] {
            ELEMENTAL_HARMONY_STR,
            IMPERIAL_RECALL,
            ELEMENTAL_EXPERT,
            ELEMENTAL_SLASH,
            NOBLE_MIND,
            ELEMENTAL_SHIFT,
            ELEMENTAL_SHIFT2
    };

    private int[] buffs = new int[] {
            LIGHTNING_ELEMENTAL,
            KNUCKLE_BOOSTER,
            LINK_MASTERY,
            ARC_CHARGER,
            SPEED_INFUSION,
            CALL_OF_CYGNUS_TB,
            GLORY_OF_THE_GUARDIANS_TB,
            PRIMAL_BOLT,
    };

    private int[] lightningBuffs = new int[] {
            LIGHTNING_ELEMENTAL,
            ELECTRIFIED,
            LIGHTNING_BOOST,
            LIGHTNING_LORD,
            THUNDER_GOD,
    };

    private int lastAttackSkill = 0;
    private byte arcChargeCDCount;

    public ThunderBreaker(Char chr) {
        super(chr);
        if(chr.getId() != 0 && isHandlerOfJob(chr.getJob())) {
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
        return JobConstants.isThunderBreaker(id);
    }



    // Buff related methods --------------------------------------------------------------------------------------------

    public void handleBuff(Client c, InPacket inPacket, int skillID, byte slv) {
        Char chr = c.getChr();
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        TemporaryStatManager tsm = c.getChr().getTemporaryStatManager();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillID) {
            case LIGHTNING_ELEMENTAL:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.CygnusElementSkill, o1);
                o2.nOption = si.getValue(SkillStat.x, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IgnoreMobpdpR, o2);
                break;
            case KNUCKLE_BOOSTER:
                o1.nOption = si.getValue(SkillStat.x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.Booster, o1);
                break;
            case ARC_CHARGER:
                o1.nOption = si.getValue(SkillStat.x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.ShadowPartner, o1);
                arcChargeCDCount = 0;
                break;
            case SPEED_INFUSION:
                TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.PartyBooster);
                tsb.setNOption(-1);
                tsb.setROption(skillID);
                tsb.setExpireTerm(1);
                tsm.putCharacterStatValue(CharacterTemporaryStat.PartyBooster, tsb.getOption());
                break;
            case CALL_OF_CYGNUS_TB:
                o1.nReason = skillID;
                o1.nValue = si.getValue(SkillStat.x, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndieStatR, o1); //Indie
                break;
            case LINK_MASTERY:
                o1.nOption = si.getValue(SkillStat.x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.DamR, o1);
                break;
            case GLORY_OF_THE_GUARDIANS_TB:
                o1.nReason = skillID;
                o1.nValue = si.getValue(SkillStat.indieDamR, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndieDamR, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(SkillStat.indieMaxDamageOverR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMaxDamageOverR, o2);
                break;
            case PRIMAL_BOLT:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.StrikerHyperElectric, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(SkillStat.indieDamR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(SkillStat.time, slv);
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndieDamR, o2);
                chr.resetSkillCoolTime(TYPHOON);
                chr.resetSkillCoolTime(GALE);
                break;

        }
        tsm.sendSetStatPacket();
    }

    public boolean isBuff(int skillID) {
        return super.isBuff(skillID) || Arrays.stream(buffs).anyMatch(b -> b == skillID);
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
        int chargeProp = getChargeProp();
        if (tsm.hasStat(CharacterTemporaryStat.CygnusElementSkill)) {
            if (hasHitMobs && Util.succeedProp(chargeProp)) {
                incrementLightningElemental(tsm);
            }
        }
        if(chr.hasSkill(LINK_MASTERY)) {
            if (hasHitMobs && skill != null) {
                giveLinkMasteryBuff(skill.getSkillId(), tsm);
            }
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case GALE:
            case TYPHOON:
                int chargeStack = tsm.getOption(CharacterTemporaryStat.IgnoreTargetDEF).mOption;
                if((tsm.getOptByCTSAndSkill(CharacterTemporaryStat.IndieDamR, GALE) == null) || (tsm.getOptByCTSAndSkill(
                    CharacterTemporaryStat.IndieDamR, TYPHOON) == null)) {
                    o1.nReason = skillID;
                    o1.nValue = chargeStack * si.getValue(SkillStat.y, slv);
                    o1.tStart = (int) System.currentTimeMillis();
                    o1.tTerm = si.getValue(SkillStat.time, slv);
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieDamR, o1); //Indie
                    tsm.sendSetStatPacket();
                }
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    private void giveLinkMasteryBuff(int skillId, TemporaryStatManager tsm) {
        Option o = new Option();
        SkillInfo linkInfo = SkillData.getSkillInfoById(LINK_MASTERY);
        if (lastAttackSkill == skillId) {
            return;
        } else {
            lastAttackSkill = skillId;
            o.nOption = linkInfo.getValue(SkillStat.x, linkInfo.getCurrentLevel());
            o.rOption = LINK_MASTERY;
            o.tOption = 10;
            tsm.putCharacterStatValue(CharacterTemporaryStat.DamR, o);
            tsm.sendSetStatPacket();
        }
    }

    private void incrementLightningElemental(TemporaryStatManager tsm) {
        Option o = new Option();
        Skill skill = chr.getSkill(LIGHTNING_ELEMENTAL);
        SkillInfo leInfo = SkillData.getSkillInfoById(skill.getSkillId());
        SkillInfo pbInfo = SkillData.getSkillInfoById(PRIMAL_BOLT);
        byte slv = (byte) skill.getCurrentLevel();
        int amount = 1;
        if(tsm.hasStat(CharacterTemporaryStat.IgnoreTargetDEF)) {
            amount = tsm.getOption(CharacterTemporaryStat.IgnoreTargetDEF).mOption;
            if(amount < getMaxCharge()) {
                amount++;
            }
        }
        o.nOption = (tsm.hasStat(CharacterTemporaryStat.StrikerHyperElectric) ? (pbInfo.getValue(SkillStat.x, slv)) : (leInfo.getValue(
            SkillStat.x, slv))) * amount;
        o.mOption = amount;
        o.rOption = LIGHTNING_ELEMENTAL;
        o.tOption = leInfo.getValue(SkillStat.y, leInfo.getCurrentLevel());
        tsm.putCharacterStatValue(CharacterTemporaryStat.IgnoreTargetDEF, o);
        tsm.sendSetStatPacket();
        reduceArcChargerCoolTime();
    }

    private void reduceArcChargerCoolTime() {
        Skill skill = chr.getSkill(ARC_CHARGER);
        if (skill == null || arcChargeCDCount >= 5) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        byte slv = (byte) skill.getCurrentLevel();

        arcChargeCDCount++;
        chr.reduceSkillCoolTime(ARC_CHARGER, (si.getValue(SkillStat.y, slv) * 1000));
        chr.chatMessage(arcChargeCDCount + "");
    }

    private Skill getLightningChargeSkill() {
        Skill skill = null;
        for (int lightningSkill : lightningBuffs) {
            if(chr.hasSkill(lightningSkill)) {
                skill = chr.getSkill(lightningSkill);
            }
        }
        return skill;
    }

    private int getChargeProp() {
        Skill skill = getLightningChargeSkill();
        if(skill != null) {
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            byte slv = (byte) skill.getCurrentLevel();
            return si.getValue(SkillStat.prop, slv);
        }
        return 0;
    }

    private int getMaxCharge() {
        int num = 0;
        for(int skill : lightningBuffs) {
            if(chr.hasSkill(skill)) {
                num++;
            }
        }
        return num;
    }

    @Override
    public int getFinalAttackSkill() {
        return 0;
    }



    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Client c, int skillID, byte slv, InPacket inPacket) {
        super.handleSkill(c, skillID, slv, inPacket);
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
                case IMPERIAL_RECALL:
                    o1.nValue = si.getValue(SkillStat.x, slv);
                    Field toField = chr.getOrCreateFieldByCurrentInstanceType(o1.nValue);
                    chr.warp(toField);
                    break;
            }
        }
    }

    @Override
    public int alterCooldownSkill(int skillId) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        switch (skillId) {
            case GALE:
            case TYPHOON:
                if (tsm.hasStat(CharacterTemporaryStat.StrikerHyperElectric)) {
                    return 0;
                }
        }
        return -1;
    }



    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {

        super.handleHit(c, inPacket, hitInfo);
    }
}
