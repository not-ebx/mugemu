package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.handlers.header.OutHeader;
import net.mugeemu.ms.life.Dragon;
import net.mugeemu.ms.life.movement.MovementInfo;

public class DragonPool {

    public static OutPacket createDragon(Dragon dragon) {
        OutPacket outPacket = new OutPacket(OutHeader.DRAGON_CREATED);

        Char owner = dragon.getOwner();

        outPacket.encodeInt(owner.getId());
        outPacket.encodePositionInt(dragon.getPosition());
        outPacket.encodeByte(4); //Move Action
        outPacket.encodeShort(owner.getFoothold()); // ignored, probably FH
        outPacket.encodeShort(owner.getJob());

        return outPacket;
    }

    public static OutPacket moveDragon(Dragon dragon, MovementInfo movementInfo) {
        OutPacket outPacket = new OutPacket(OutHeader.DRAGON_MOVE);

        Char owner = dragon.getOwner();

        outPacket.encodeInt(owner.getId());
        outPacket.encode(movementInfo);

        return outPacket;
    }

    public static OutPacket removeDragon(Dragon dragon) {
        OutPacket outPacket = new OutPacket(OutHeader.DRAGON_REMOVE);

        outPacket.encodeInt(dragon.getOwner().getId());

        return outPacket;
    }
}
