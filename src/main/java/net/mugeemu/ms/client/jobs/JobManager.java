package net.mugeemu.ms.client.jobs;

import net.mugeemu.ms.client.character.CharacterStat;
import net.mugeemu.ms.client.character.skills.info.AttackInfo;
import net.mugeemu.ms.client.jobs.adventurer.*;
import net.mugeemu.ms.client.jobs.cygnus.*;
import net.mugeemu.ms.client.jobs.legend.*;
import net.mugeemu.ms.client.jobs.resistance.*;
import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.character.Char;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 12/14/2017.
 */
public class JobManager {

    private static final Map<JobConstants.JobEnum, Class<? extends Job>> jobClassMap = Map
        .<JobConstants.JobEnum, Class<? extends Job>>ofEntries(
        Map.entry(JobConstants.JobEnum.BEGINNER, Beginner.class),

        Map.entry(JobConstants.JobEnum.WARRIOR, Warrior.class),
        Map.entry(JobConstants.JobEnum.PAGE, Warrior.class),
        Map.entry(JobConstants.JobEnum.WHITEKNIGHT, Warrior.class),
        Map.entry(JobConstants.JobEnum.PALADIN, Warrior.class),
        Map.entry(JobConstants.JobEnum.FIGHTER, Warrior.class),
        Map.entry(JobConstants.JobEnum.CRUSADER, Warrior.class),
        Map.entry(JobConstants.JobEnum.HERO, Warrior.class),
        Map.entry(JobConstants.JobEnum.SPEARMAN, Warrior.class),
        Map.entry(JobConstants.JobEnum.DRAGONKNIGHT, Warrior.class),
        Map.entry(JobConstants.JobEnum.DARKKNIGHT, Warrior.class),

        Map.entry(JobConstants.JobEnum.MAGICIAN, Magician.class),
        Map.entry(JobConstants.JobEnum.CLERIC, Magician.class),
        Map.entry(JobConstants.JobEnum.PRIEST, Magician.class),
        Map.entry(JobConstants.JobEnum.BISHOP, Magician.class),
        Map.entry(JobConstants.JobEnum.FP_WIZARD, Magician.class),
        Map.entry(JobConstants.JobEnum.FP_MAGE, Magician.class),
        Map.entry(JobConstants.JobEnum.FP_ARCHMAGE, Magician.class),
        Map.entry(JobConstants.JobEnum.IL_WIZARD, Magician.class),
        Map.entry(JobConstants.JobEnum.IL_MAGE, Magician.class),
        Map.entry(JobConstants.JobEnum.IL_ARCHMAGE, Magician.class),

        Map.entry(JobConstants.JobEnum.BOWMAN, Archer.class),
        Map.entry(JobConstants.JobEnum.HUNTER, Archer.class),
        Map.entry(JobConstants.JobEnum.RANGER, Archer.class),
        Map.entry(JobConstants.JobEnum.BOWMASTER, Archer.class),
        Map.entry(JobConstants.JobEnum.CROSSBOWMAN, Archer.class),
        Map.entry(JobConstants.JobEnum.SNIPER, Archer.class),
        Map.entry(JobConstants.JobEnum.MARKSMAN, Archer.class),

        Map.entry(JobConstants.JobEnum.THIEF, Thief.class),
        Map.entry(JobConstants.JobEnum.ASSASSIN, Thief.class),
        Map.entry(JobConstants.JobEnum.HERMIT, Thief.class),
        Map.entry(JobConstants.JobEnum.NIGHTLORD, Thief.class),
        Map.entry(JobConstants.JobEnum.BANDIT, Thief.class),
        Map.entry(JobConstants.JobEnum.CHIEFBANDIT, Thief.class),
        Map.entry(JobConstants.JobEnum.SHADOWER, Thief.class),

        Map.entry(JobConstants.JobEnum.PIRATE, Pirate.class),
        Map.entry(JobConstants.JobEnum.BRAWLER, Pirate.class),
        Map.entry(JobConstants.JobEnum.MARAUDER, Pirate.class),
        Map.entry(JobConstants.JobEnum.BUCCANEER, Pirate.class),
        Map.entry(JobConstants.JobEnum.GUNSLINGER, Pirate.class),
        Map.entry(JobConstants.JobEnum.OUTLAW, Pirate.class),
        Map.entry(JobConstants.JobEnum.CORSAIR, Pirate.class),

        Map.entry(JobConstants.JobEnum.LEGEND, Legend.class),
        Map.entry(JobConstants.JobEnum.ARAN1, Aran.class),
        Map.entry(JobConstants.JobEnum.ARAN2, Aran.class),
        Map.entry(JobConstants.JobEnum.ARAN3, Aran.class),
        Map.entry(JobConstants.JobEnum.ARAN4, Aran.class),

        Map.entry(JobConstants.JobEnum.EVAN_NOOB, Evan.class),
        Map.entry(JobConstants.JobEnum.EVAN1, Evan.class),
        Map.entry(JobConstants.JobEnum.EVAN2, Evan.class),
        Map.entry(JobConstants.JobEnum.EVAN3, Evan.class),
        Map.entry(JobConstants.JobEnum.EVAN4, Evan.class),

        Map.entry(JobConstants.JobEnum.MERCEDES, Mercedes.class),
        Map.entry(JobConstants.JobEnum.MERCEDES1, Mercedes.class),
        Map.entry(JobConstants.JobEnum.MERCEDES2, Mercedes.class),
        Map.entry(JobConstants.JobEnum.MERCEDES3, Mercedes.class),
        Map.entry(JobConstants.JobEnum.MERCEDES4, Mercedes.class),

        Map.entry(JobConstants.JobEnum.DEMON_SLAYER, Demon.class),
        Map.entry(JobConstants.JobEnum.DEMON_SLAYER1, Demon.class),
        Map.entry(JobConstants.JobEnum.DEMON_SLAYER2, Demon.class),
        Map.entry(JobConstants.JobEnum.DEMON_SLAYER3, Demon.class),
        Map.entry(JobConstants.JobEnum.DEMON_SLAYER4, Demon.class)
    );

    private short id;

    public JobConstants.JobEnum getJobEnum() {
        return JobConstants.getJobEnumById(getId());
    }
    public void setDefaultCharStatValues(CharacterStat characterStat) {
        characterStat.setLevel(1);
        characterStat.setStr(12);
        characterStat.setDex(5);
        characterStat.setInt(4);
        characterStat.setLuk(4);
        characterStat.setMaxHp(50);
        characterStat.setHp(50);
        characterStat.setMaxMp(5);
        characterStat.setMp(5);
    }

    public static void handleAtt(Client c, AttackInfo attackInfo) {
        try {
            Job job = createJobHandler(c.getChr().getJob(), c.getChr());
            if (job != null) {
                job.handleAttack(c, attackInfo);
            }
        } catch (
            InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e
        ) {
            throw new RuntimeException(e);
        }
    }

    public static void handleSkill(Client c, InPacket inPacket) {
        try {
            Job job = createJobHandler(c.getChr().getJob(), c.getChr());
            if (job != null) {
                inPacket.decodeInt(); //crc
                int skillID = inPacket.decodeInt();
                byte slv = inPacket.decodeByte();
                job.handleSkill(c, skillID, slv, inPacket);
            }
        } catch (
            InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e
        ) {
            throw new RuntimeException(e);
        }
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public static Job getJobById(short id, Char chr) {
        try {
            return createJobHandler(id, chr);
        } catch (
            InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e
        ) {
            throw new RuntimeException(e);
        }
    }

    public static Job createJobHandler(
        short id, Char chr
    ) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JobConstants.JobEnum jobEnum = JobConstants.getJobEnumById(id);
        if(jobEnum != null && jobClassMap.containsKey(jobEnum)){
            Class<? extends Job> jobClass = jobClassMap.get(jobEnum);
            return jobClass
                .getDeclaredConstructor(Char.class)
                .newInstance(chr);
        }
        return null;
    }
}