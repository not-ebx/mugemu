package net.mugeemu.ms.life.movement;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.life.Life;

/**
 * Created on 1/2/2018.
 */
public class MovementTeleport extends MovementBase {
    public MovementTeleport(InPacket inPacket, byte command) {
        super();
        this.command = command;

        short x = inPacket.decodeShort();
        short y = inPacket.decodeShort();
        position = new Position(x, y);

        fh = inPacket.decodeShort();

        moveAction = inPacket.decodeByte();

        // Unsure about those 2.
        elapse = inPacket.decodeShort();
        //forcedStop = inPacket.decodeByte();
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getCommand());
        outPacket.encodePosition(getPosition());
        outPacket.encodeShort(getFh());
        outPacket.encodeByte(getMoveAction());
        outPacket.encodeShort(getDuration());
        outPacket.encodeByte(getForcedStop());
    }

    @Override
    public void applyTo(Char chr) {
        chr.setPosition(getPosition());
        chr.setFoothold(getFh());
        chr.setMoveAction(getMoveAction());
    }

    @Override
    public void applyTo(Life life) {
        life.setPosition(getPosition());
        life.setFh(getFh());
        life.setMoveAction(getMoveAction());
    }
}
