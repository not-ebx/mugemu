package net.mugeemu.ms.client.trunk;

import net.mugeemu.ms.connection.OutPacket;

/**
 * Created on 4/7/2018.
 */
public class TrunkOpen implements TrunkDlg {

    private int npcTemplateID;
    private Trunk trunk;

    public TrunkOpen(int npcTemplateID, Trunk trunk) {
        this.npcTemplateID = npcTemplateID;
        this.trunk = trunk;
    }

    @Override
    public TrunkType getType() {
        return TrunkType.TrunkRes_OpenTrunkDlg;
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(npcTemplateID);
        trunk.encodeItems(outPacket);
    }
}
