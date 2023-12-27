package net.mugeemu.ms.client.character.runestones;

import net.mugeemu.ms.connection.packet.FieldPacket;
import net.mugeemu.ms.connection.packet.UserLocal;
import net.mugeemu.ms.constants.GameConstants;
import net.mugeemu.ms.handlers.EventManager;
import net.mugeemu.ms.loaders.SkillData;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.character.skills.Option;
import net.mugeemu.ms.client.character.skills.Skill;
import net.mugeemu.ms.client.character.skills.info.SkillInfo;
import net.mugeemu.ms.client.character.skills.temp.TemporaryStatManager;
import net.mugeemu.ms.enums.ChatType;
import net.mugeemu.ms.enums.RuneType;
import net.mugeemu.ms.life.mob.Mob;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.world.field.Field;
import net.mugeemu.ms.world.field.Foothold;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.mugeemu.ms.client.character.skills.SkillStat.*;
import static net.mugeemu.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created by Asura on 6-6-2018.
 */
public class RuneStone {
    private RuneType runeType;
    private Position position;
    private boolean flip;
    private ScheduledFuture thunderTimer;

    public static final int LIBERATE_THE_SWIFT_RUNE = 80001427;
    public static final int LIBERATE_THE_RECOVERY_RUNE = 80001428;
    public static final int LIBERATE_THE_DESTRUCTIVE_RUNE = 80001431;
    public static final int LIBERATE_THE_DESTRUCTIVE_RUNE_BUFF = 80001432;
    public static final int LIBERATE_THE_RUNE_OF_THUNDER = 80001752;
    public static final int LIBERATE_THE_RUNE_OF_MIGHT = 80001753;
    public static final int LIBERATE_THE_RUNE_OF_DARKNESS = 80001754;
    public static final int LIBERATE_THE_RUNE_OF_RICHES = 80001755;
    public static final int LIBERATE_THE_RUNE_OF_HORDES = 80001874;
    public static final int LIBERATE_THE_RUNE_OF_SKILL = 80001875;

    public static final int LIBERATE_THE_RUNE_OF_MIGHT_2 = 80001757;
    public static final int LIBERATE_THE_RUNE_OF_THUNDER_2 = 80001762;

    public RuneType getRuneType() {
        return runeType;
    }

    public void setRuneType(RuneType runeType) {
        this.runeType = runeType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public void spawnRune(Char chr) {
        chr.write(FieldPacket.runeStoneAppear(this));
    }

    public void despawnRune(Char chr) {
        chr.write(FieldPacket.runeStoneClearAndAllRegister());
    }

    public RuneStone getRandomRuneStone(Field field) {
        RuneStone runeStone = new RuneStone();
        runeStone.setRuneType(RuneType.getByVal((byte) new Random().nextInt(RuneType.values().length)));

        List<Foothold> listOfFootHolds = new ArrayList<>(field.getNonWallFootholds());
        Foothold foothold = Util.getRandomFromCollection(listOfFootHolds);
        Position position = foothold.getRandomPosition();

        runeStone.setPosition(position);
        runeStone.setFlip(false);
        return runeStone;
    }


    private void applyRuneSwiftness(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = SkillData.getSkillDeepCopyById(LIBERATE_THE_SWIFT_RUNE);
        int skillID = skill.getSkillId();
        skill.setCurrentLevel(1);
        byte slv = (byte) skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();

        o1.nReason = skillID;
        o1.nValue = si.getValue(indieBooster, slv);
        o1.tStart = (int) System.currentTimeMillis();
        o1.tTerm = si.getValue(time, slv);
        tsm.putCharacterStatValue(IndieBooster, o1);

        o3.nReason = skillID;
        o3.nValue = si.getValue(indieJump, slv);
        o3.tStart = (int) System.currentTimeMillis();
        o3.tTerm = si.getValue(time, slv);
        tsm.putCharacterStatValue(IndieJump, o3);

        o4.nReason = skillID;
        o4.nValue = si.getValue(indieSpeed, slv);
        o4.tStart = (int) System.currentTimeMillis();
        o4.tTerm = si.getValue(time, slv);
        tsm.putCharacterStatValue(IndieSpeed, o4);

        tsm.sendSetStatPacket();
    }

    private void applyRuneRecovery(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = SkillData.getSkillDeepCopyById(LIBERATE_THE_RECOVERY_RUNE);
        int skillID = skill.getSkillId();
        skill.setCurrentLevel(1);
        byte slv = (byte) skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();

        // HP Regen handled in Job.java : handleAttack

        o3.nOption = si.getValue(ignoreMobDamR, slv);
        o3.rOption = skillID;
        o3.tOption = si.getValue(time, slv);
        tsm.putCharacterStatValue(IgnoreMobDamR, o3);

        o4.nReason = skillID;
        o4.nValue = si.getValue(indieAsrR, slv);
        o4.tStart = (int) System.currentTimeMillis();
        o4.tTerm = si.getValue(time, slv);
        tsm.putCharacterStatValue(IndieAsrR, o4);
        tsm.putCharacterStatValue(IndieTerR, o4);

        tsm.sendSetStatPacket();
    }

    private void applyRuneHordes(Char chr) {
        Skill skill = SkillData.getSkillDeepCopyById(LIBERATE_THE_RUNE_OF_HORDES);
        byte slv = 1;
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());

        // Map Effect
        int duration = si.getValue(time, slv);
        int mobRateMultiplier = si.getValue(incMobRateDummy, slv);
        chr.getField().runeStoneHordeEffect(mobRateMultiplier, duration);
    }

    private void applyRuneThunder(Char chr) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = SkillData.getSkillDeepCopyById(LIBERATE_THE_RUNE_OF_THUNDER_2);
        int skillID = skill.getSkillId();
        skill.setCurrentLevel(1);
        byte slv = (byte) skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        Option o1 = new Option();

        // RandAreaAttack Buff
        o1.nOption = 1;
        o1.rOption = skillID;
        o1.tOption = SkillData.getSkillInfoById(80001756).getValue(time, slv);
        tsm.putCharacterStatValue(RandAreaAttack, o1);
        tsm.sendSetStatPacket();

        int fieldID = chr.getFieldID();
        randAreaAttack(fieldID, tsm, chr);
    }

    private void randAreaAttack(int fieldID, TemporaryStatManager tsm, Char chr) {
        if((tsm.getOptByCTSAndSkill(RandAreaAttack, LIBERATE_THE_RUNE_OF_THUNDER_2) == null) || fieldID != chr.getFieldID()) {
            return;
        }

        Mob randomMob = Util.getRandomFromCollection(chr.getField().getMobs());
        chr.write(UserLocal.userRandAreaAttackRequest(randomMob, LIBERATE_THE_RUNE_OF_THUNDER_2));

        if(thunderTimer != null && !thunderTimer.isDone()) {
            thunderTimer.cancel(true);
        }
        thunderTimer = EventManager.addEvent(() -> randAreaAttack(fieldID, tsm, chr), GameConstants.THUNDER_RUNE_ATTACK_DELAY, TimeUnit.SECONDS);
    }

}
