package net.mugeemu.ms.life.mob.boss.demian.stigma;

import net.mugeemu.ms.connection.packet.DemianFieldPacket;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.world.field.Field;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.life.Life;

import java.util.Random;

/**
 * Created on 18-8-2019.
 *
 * @author Asura
 */
public class DemianStigmaIncinerateObject extends Life {

    public DemianStigmaIncinerateObject(int templateId) {
        super(templateId);
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar) {
        Field field = getField();
        setPosition(new Position(new Random().nextInt(1400) + 100, 16)); // randomise position before spawning
        field.broadcastPacket(DemianFieldPacket.stigmaIncinerateObjectPacket(this, true));
    }

    @Override
    public void broadcastLeavePacket() {
        Field field = getField();
        field.broadcastPacket(DemianFieldPacket.stigmaIncinerateObjectPacket(null, true)); // null -> remove
    }
}
