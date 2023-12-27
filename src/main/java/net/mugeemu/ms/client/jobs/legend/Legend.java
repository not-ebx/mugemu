package net.mugeemu.ms.client.jobs.legend;

import net.mugeemu.ms.client.character.info.HitInfo;
import net.mugeemu.ms.client.character.skills.info.AttackInfo;
import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.jobs.Job;

/**
 * Created on 12/14/2017.
 */
public class Legend extends Job {
    public Legend(Char chr) {
        super(chr);
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
        return id == JobConstants.JobEnum.LEGEND.getJobId();
    }

    @Override
    public int getFinalAttackSkill() {
        return 0;
    }

    @Override
    public boolean isBuff(int skillID) {
        return super.isBuff(skillID);
    }
}
