package net.mugeemu.ms.life.movement;


import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.life.Life;

/**
 * Created on 1/2/2018.
 * These classes + children/parents are basically the same as Mushy, credits to @MaxCloud.
 */
public class MovementNormal extends MovementBase {
    public MovementNormal(InPacket inPacket, byte command) {
        super();
        this.command = command;

        short x = inPacket.decodeShort();
        short y = inPacket.decodeShort();
        position = new Position(x, y);

        short xv = inPacket.decodeShort();
        short yv = inPacket.decodeShort();
        vPosition = new Position(xv, yv);

        fh = inPacket.decodeShort();

        //if (command == 15 || command == 17) {
        if (command == 14) {
            footStart = inPacket.decodeShort();
        }

        short xoffset = inPacket.decodeShort();
        short yoffset = inPacket.decodeShort();
        offset = new Position(xoffset, yoffset);

        moveAction = inPacket.decodeByte();
        elapse = inPacket.decodeShort();
        //forcedStop = inPacket.decodeByte();
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getCommand());
        outPacket.encodePosition(getPosition());
        outPacket.encodePosition(getVPosition());
        outPacket.encodeShort(getFh());
        if (getCommand() == 15 || getCommand() == 17) {
            outPacket.encodeShort(getFootStart());
        }
        outPacket.encodePosition(getOffset());
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
        life.setvPosition(getVPosition());
        life.setFh(getFh());
        life.setMoveAction(getMoveAction());
    }
}
