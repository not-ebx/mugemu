package net.mugeemu.ms.life.movement;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.life.Life;

/**
 * Created on 1/2/2018.
 */
public class MovementJump extends MovementBase {
    public MovementJump(InPacket inPacket, byte command) {
        super();
        this.command = command;

        short vx = inPacket.decodeShort();
        short vy = inPacket.decodeShort();
        position = new Position(vx, vy);

        if (command == 21 || command == 22) {
            footStart = inPacket.decodeShort();
        }

        moveAction = inPacket.decodeByte();
        elapse = inPacket.decodeShort();
        forcedStop = inPacket.decodeByte();
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getCommand());
        outPacket.encodePosition(getVPosition());
        if (getCommand() == 21 || getCommand() == 22) {
            outPacket.encodeShort(getFootStart());
        }
        outPacket.encodeByte(getMoveAction());
        outPacket.encodeShort(getDuration());
        outPacket.encodeByte(getForcedStop());
    }

    @Override
    public void applyTo(Char chr) {
        chr.setPosition(getPosition());
        chr.setMoveAction(getMoveAction());
    }

    @Override
    public void applyTo(Life life) {
        life.setPosition(getPosition());
        life.setMoveAction(getMoveAction());
    }
}
