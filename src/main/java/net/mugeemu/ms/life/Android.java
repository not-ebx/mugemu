package net.mugeemu.ms.life;

import net.mugeemu.ms.client.character.items.BodyPart;
import net.mugeemu.ms.client.character.items.Inventory;
import net.mugeemu.ms.client.character.items.Item;
import net.mugeemu.ms.connection.Encodable;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.connection.packet.AndroidPacket;
import net.mugeemu.ms.loaders.containerclasses.AndroidInfo;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.client.character.Char;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 4/14/2018.
 */
public class Android extends Life implements Encodable {

    private static final int ANDROID_ITEM_SIZE = 7;

    private Char ownerChar;
    private short skin;
    private short hair;
    private short face;
    private String name;

    public Android(int templateId) {
        super(templateId);
    }

    public Android(int templateId, Char ownerChar, short skin, short hair, short face, String name) {
        super(templateId);
        this.ownerChar = ownerChar;
        this.skin = skin;
        this.hair = hair;
        this.face = face;
        this.name = name;
    }

    public Android(Char chr, AndroidInfo androidInfo) {
        super(androidInfo.getId());
        this.ownerChar = chr;
        this.skin = (short) (int) Util.getRandomFromCollection(androidInfo.getSkins()); // double cast...
        this.hair = (short) (int) Util.getRandomFromCollection(androidInfo.getHairs());
        this.face = (short) (int) Util.getRandomFromCollection(androidInfo.getFaces());
        this.name = "Android";
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getTemplateId());
        outPacket.encodePosition(getPosition());
        outPacket.encodeByte(getMoveAction());
        outPacket.encodeShort(getFh());
        encodeAndroidInfo(outPacket);
        List<Integer> androidItems = getItems(); // size always 7
        for (int itemId : androidItems) {
            outPacket.encodeInt(itemId);
        }
    }

    public void encodeAndroidInfo(OutPacket outPacket) {
        outPacket.encodeShort(getSkin());
        outPacket.encodeShort(getHair());
        outPacket.encodeShort(getFace());
        outPacket.encodeString(getName());
    }

    public Char getOwnerChar() {
        return ownerChar;
    }

    public void setOwnerChar(Char ownerChar) {
        this.ownerChar = ownerChar;
    }

    public short getSkin() {
        return skin;
    }

    public void setSkin(short skin) {
        this.skin = skin;
    }

    public short getHair() {
        return hair;
    }

    public void setHair(short hair) {
        this.hair = hair;
    }

    public short getFace() {
        return face;
    }

    public void setFace(short face) {
        this.face = face;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChrId() {
        return ownerChar.getId();
    }

    public List<Integer> getItems() {
        List<Integer> items = new ArrayList<>();
        Inventory inv = getOwnerChar().getEquippedInventory();
        for (int i = BodyPart.APBase.getVal(); i < BodyPart.APEnd.getVal(); i++) {
            Item item = inv.getItemBySlot(i);
            items.add(item == null ? 0 : item.getItemId());
        }
        return items;
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar) {
        OutPacket outPacket = AndroidPacket.created(this);
        if (onlyChar == null) {
            getField().broadcastPacket(outPacket);
        } else {
            onlyChar.write(outPacket);
        }
    }

    @Override
    public void broadcastLeavePacket() {
        getField().broadcastPacket(AndroidPacket.removed(this));
    }
}
