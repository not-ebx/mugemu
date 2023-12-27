package net.mugeemu.ms.client.character.items;


import net.mugeemu.ms.client.character.skills.Option;
import net.mugeemu.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.mugeemu.ms.client.character.skills.temp.TemporaryStatManager;
import net.mugeemu.ms.loaders.ItemData;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.enums.SpecStat;
import net.mugeemu.ms.enums.Stat;

import java.util.Map;

public class ItemBuffs {
    public static void giveItemBuffsFromItemID(Char chr, TemporaryStatManager tsm, int itemID) {
        Map<SpecStat, Integer> specStats = ItemData.getItemInfoByID(itemID).getSpecStats();
        long time = specStats.getOrDefault(SpecStat.time, 0) / 1000;
        for (Map.Entry<SpecStat, Integer> entry : specStats.entrySet()) {
            SpecStat ss = entry.getKey();
            int value = entry.getValue();
            Option o = new Option(-itemID, time);
            o.nOption = value;
            o.nValue = value;
            switch (ss) {
                case hp:
                    chr.heal(value);
                    break;
                case hpR:
                    chr.heal((int) ((value / 100D) * chr.getStat(Stat.mhp)));
                    break;
                case mp:
                    chr.healMP(value);
                    break;
                case mpR:
                    chr.healMP((int) ((value / 100D) * chr.getStat(Stat.mmp)));
                    break;
                case eva:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.EVA, o);
                    break;
                case speed:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.Speed, o);
                    break;
                case pad:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.PAD, o);
                    break;
                case mad:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.MAD, o);
                    break;
                case pdd:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.PDD, o);
                    break;
                case mdd:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.MDD, o);
                    break;
                case acc:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.ACC, o);
                    break;
                case jump:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.Jump, o);
                    break;
                case imhp:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.MaxHP, o);
                    break;
                case immp:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.MaxMP, o);
                    break;
                case indieAllStat:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieAllStat, o);
                    break;
                case indieSpeed:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieSpeed, o);
                    break;
                case indieJump:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieJump, o);
                    break;
                case indieSTR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieSTR, o);
                    break;
                case indieDEX:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieDEX, o);
                    break;
                case indieINT:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieINT, o);
                    break;
                case indieLUK:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieLUK, o);
                    break;
                case indiePad:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePAD, o);
                    break;
                case indiePdd:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePDD, o);
                    break;
                case indieMad:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMAD, o);
                    break;
                case indieMdd:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMDD, o);
                    break;
                case indieBDR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBDR, o);
                    break;
                case indieIgnoreMobpdpR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieIgnoreMobpdpR, o);
                    break;
                case indieStatR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieStatR, o);
                    break;
                case indieMhp:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMHP, o);
                    break;
                case indieMmp:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMMP, o);
                    break;
                case indieBooster:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieBooster, o);
                    break;
                case indieAcc:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieACC, o);
                    break;
                case indieEva:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieEVA, o);
                    break;
                case indieAllSkill:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.CombatOrders, o);
                    break;
                case indieMhpR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMHPR, o);
                    break;
                case indieMmpR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMMPR, o);
                    break;
                case indieStance:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieStance, o);
                    break;
                case indieForceSpeed:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieForceSpeed, o);
                    break;
                case indieForceJump:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieForceJump, o);
                    break;
                case indieQrPointTerm:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieQrPointTerm, o);
                    break;
                case indieWaterSmashBuff:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieUNK1, o);
                    break;
                case padRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePADR, o);
                    break;
                case madRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMADR, o);
                    break;
                case pddRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndiePDDR, o);
                    break;
                case mddRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMDDR, o);
                    break;
                case accRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.ACCR, o);
                    break;
                case evaRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.EVAR, o);
                    break;
                case mhpR:
                case mhpRRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMHPR, o);
                    break;
                case mmpR:
                case mmpRRate:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.IndieMHPR, o);
                    break;
                case booster:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.Booster, o);
                    break;
                case expinc:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.ExpBuffRate, o);
                    break;
                case str:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.STR, o);
                    break;
                case dex:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.DEX, o);
                    break;
                case inte:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.INT, o);
                    break;
                case luk:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.LUK, o);
                    break;
                case asrR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.AsrRByItem, o);
                    break;
                case bdR:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.BdR, o);
                    break;
                case prob:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.ItemUpByItem, o);
                    tsm.putCharacterStatValue(CharacterTemporaryStat.MesoUpByItem, o);
                    break;
                case inflation:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.Inflation, o);
                    break;
                case morph:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.Morph, o);
                    break;
                case repeatEffect:
                    tsm.putCharacterStatValue(CharacterTemporaryStat.RepeatEffect, o);
                    break;
            }
        }
        tsm.sendSetStatPacket();
    }
}
