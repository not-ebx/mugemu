package net.mugeemu.ms.client.character.skills;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.life.mob.Mob;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.util.Util;

/**
 * Created on 1/13/2018.
 */
public class PsychicLockBall {
    public boolean success = true;
    public int localKey;
    public Mob mob;
    public int psychicLockKey;
    public short stuffID;
    public short usableCount;
    public byte posRelID;
    public Position start;
    public Position rel;
    public int time;

    public void encode(OutPacket outPacket) {
        boolean hasMob = mob != null;
        outPacket.encodeByte(success);
        outPacket.encodeInt(localKey);
        outPacket.encodeInt(psychicLockKey);
        outPacket.encodeInt(hasMob ? mob.getObjectId() : 0);
        outPacket.encodeShort(stuffID);
        outPacket.encodeInt(hasMob ? Util.maxInt(mob.getMaxHp()) : 0);
        outPacket.encodeInt(hasMob ? Util.maxInt(mob.getHp()) : 0);
        outPacket.encodeByte(posRelID);
        outPacket.encodePositionInt(start);
        outPacket.encodePositionInt(rel);
        outPacket.encodeByte(0);
    }
}
