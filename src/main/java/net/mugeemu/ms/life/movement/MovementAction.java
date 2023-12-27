package net.mugeemu.ms.life.movement;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.life.Life;

/**
 * Created on 1/2/2018.
 */
public class MovementAction extends MovementBase {
    public MovementAction(InPacket inPacket, byte command) {
        super();
        this.command = command;
        this.position = new Position(0, 0);

        moveAction = inPacket.decodeByte();
        elapse = inPacket.decodeShort();
        forcedStop = inPacket.decodeByte();
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getCommand());
        outPacket.encodeByte(getMoveAction());
        outPacket.encodeShort(getDuration());
        outPacket.encodeByte(getForcedStop());
    }

    @Override
    public void applyTo(Char chr) {
        chr.setMoveAction(moveAction);
    }

    @Override
    public void applyTo(Life life) {
        life.setMoveAction(moveAction);
    }


}
