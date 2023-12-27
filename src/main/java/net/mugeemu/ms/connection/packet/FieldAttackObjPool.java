package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.handlers.header.OutHeader;
import net.mugeemu.ms.life.FieldAttackObj;

/**
 * @author Sjonnie
 * Created on 8/19/2018.
 */
public class FieldAttackObjPool {

    public static OutPacket objCreate(FieldAttackObj fieldAttackObj) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_ATTACK_CREATE);

        outPacket.encode(fieldAttackObj);

        return outPacket;
    }

    public static OutPacket objRemoveByKey(int objectID) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_ATTACK_REMOVE_BY_KEY);

        outPacket.encodeInt(objectID);

        return outPacket;
    }

    public static OutPacket setAttack(int objectId, int attackIdx) {
        OutPacket outPacket = new OutPacket(OutHeader.FIELD_ATTACK_SET_ATTACK);

        outPacket.encodeInt(objectId);
        outPacket.encodeInt(attackIdx);

        return outPacket;
    }
}
