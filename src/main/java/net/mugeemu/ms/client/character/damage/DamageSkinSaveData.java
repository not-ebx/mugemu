package net.mugeemu.ms.client.character.damage;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.constants.ItemConstants;
import net.mugeemu.ms.loaders.StringData;

import jakarta.persistence.*;

/**
 * Created on 4/5/2018.
 */
@Entity
@Table(name = "damageskinsavedatas")
public class DamageSkinSaveData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int damageSkinID;
    private int itemID;
    private boolean notSave;
    private String description;

    public DamageSkinSaveData() {
        this.damageSkinID = -1;
        this.notSave = true;
    }

    public DamageSkinSaveData(int damageSkinID, int itemID, boolean notSave, String description) {
        this.damageSkinID = damageSkinID;
        this.itemID = itemID;
        this.notSave = notSave;
        this.description = description;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getDamageSkinID());
        outPacket.encodeInt(getItemID());
        outPacket.encodeByte(isNotSave());
        outPacket.encodeString(getDescription());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDamageSkinID() {
        return damageSkinID;
    }

    public void setDamageSkinID(int damageSkinID) {
        this.damageSkinID = damageSkinID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public boolean isNotSave() {
        return notSave;
    }

    public void setNotSave(boolean notSave) {
        this.notSave = notSave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static DamageSkinSaveData getByItemID(int itemID) {
        return new DamageSkinSaveData(
            ItemConstants.getDamageSkinIDByItemID(itemID), itemID, false,
                StringData.getItemStringById(itemID));
    }
}
