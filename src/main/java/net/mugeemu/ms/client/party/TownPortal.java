package net.mugeemu.ms.client.party;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.Position;

/**
 * Created on 3/19/2018.
 */
public class TownPortal {

    private int townID;
    private int fieldID;
    private int skillID;
    private Position fieldPortal;

    public int getTownID() {
        return townID;
    }

    public void setTownID(int townID) {
        this.townID = townID;
    }

    public int getFieldID() {
        return fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public Position getFieldPortal() {
        return fieldPortal;
    }

    public void setFieldPortal(Position fieldPortal) {
        this.fieldPortal = fieldPortal;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getTownID());
        outPacket.encodeInt(getFieldID());
        outPacket.encodeInt(getSkillID());
        outPacket.encodePosition(getFieldPortal());
    }

}
