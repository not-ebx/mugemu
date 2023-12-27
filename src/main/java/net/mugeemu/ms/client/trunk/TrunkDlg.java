package net.mugeemu.ms.client.trunk;

import net.mugeemu.ms.connection.OutPacket;

/**
 * Created on 4/7/2018.
 */
public interface TrunkDlg {

    TrunkType getType();

    void encode(OutPacket outPacket);
}
