package net.mugeemu.ms.life;

import net.mugeemu.ms.connection.Encodable;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.connection.packet.FieldAttackObjPool;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.client.character.Char;

/**
 * @author Sjonnie
 * Created on 8/19/2018.
 */
public class FieldAttackObj extends Life implements Encodable {

    private int ownerID;
    private int reserveID;

    public FieldAttackObj(int templateId) {
        super(templateId);
    }

    public FieldAttackObj(int templateId, int ownerID, Position position, boolean flip) {
        super(templateId);
        this.ownerID = ownerID;
        setPosition(position);
        setFlip(flip);
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getObjectId());
        outPacket.encodeInt(getTemplateId());
        outPacket.encodeInt(getOwnerID());
        outPacket.encodeInt(getReserveID());
        outPacket.encodePositionInt(getPosition());
        outPacket.encodeByte(isFlip());
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getReserveID() {
        return reserveID;
    }

    public void setReserveID(int reserveID) {
        this.reserveID = reserveID;
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar) {
        onlyChar.write(FieldAttackObjPool.objCreate(this));
    }

    @Override
    public void broadcastLeavePacket() {
        getField().broadcastPacket(FieldAttackObjPool.objRemoveByKey(this.getObjectId()));
    }
}
