package net.mugeemu.ms.life.movement;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.life.Life;

/**
 * Created on 1/2/2018.
 */
public class MovementStatChange extends MovementBase {
    public MovementStatChange(InPacket inPacket, byte command) {
        super();
        this.command = command;
        this.position = new Position(0, 0);

        this.stat = inPacket.decodeByte();
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getCommand());
        outPacket.encodeByte(getStat());
    }

    @Override
    public void applyTo(Char chr) {

    }

    @Override
    public void applyTo(Life life) {

    }
}
