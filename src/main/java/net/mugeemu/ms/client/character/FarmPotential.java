package net.mugeemu.ms.client.character;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.FileTime;

/**
 * Created on 12/20/2017.
 */
public class FarmPotential {
    public void encode(OutPacket outPacket) {
        int size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeInt(0); // dwMonsterID
            outPacket.encodeFT(FileTime.fromType(FileTime.Type.ZERO_TIME)); // potentialExpire
        }
    }
}
