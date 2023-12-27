package net.mugeemu.ms.client.jobs.adventurer;

import net.mugeemu.ms.client.character.CharacterStat;
import net.mugeemu.ms.client.character.info.HitInfo;
import net.mugeemu.ms.client.character.skills.Skill;
import net.mugeemu.ms.client.character.skills.info.AttackInfo;
import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.loaders.SkillData;
import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.jobs.Job;

/**
 * Created on 12/14/2017.
 */
public class Beginner extends Job {
    public static final int RECOVERY = 1001;
    public static final int NIMBLE_FEET = 1002;
    public static final int THREE_SNAILS = 1000;

    private int[] addedSkills = new int[] {
        RECOVERY,
        NIMBLE_FEET,
        THREE_SNAILS
    };

    public Beginner(Char chr) {
        super(chr);

        if (chr.getId() != 0 && isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setRootId(0);
                    skill.setMasterLevel(3);
                    skill.setMaxLevel(3);
                    chr.addSkill(skill);
                }
            }
        }
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        super.handleAttack(c, attackInfo);
    }

    @Override
    public void handleSkill(Client c, int skillID, byte slv, InPacket inPacket) {
        super.handleSkill(c, skillID, slv, inPacket);
    }

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        super.handleHit(c, inPacket, hitInfo);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        JobConstants.JobEnum job = JobConstants.JobEnum.getJobById(id);
        return job == JobConstants.JobEnum.BEGINNER;
    }

    @Override
    public int getFinalAttackSkill() {
        return 0;
    }

    @Override
    public boolean isBuff(int skillID) {
        return super.isBuff(skillID);
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        if (chr.getSubJob() == 1) {
            cs.setPosMap(103050900);
        } else if (chr.getSubJob() == 2) {
            cs.setPosMap(3000600);
        } else {
            cs.setPosMap(10000);
        }
    }
}
