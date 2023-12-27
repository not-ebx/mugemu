package net.mugeemu.ms.life;

import net.mugeemu.ms.connection.packet.DragonPool;
import net.mugeemu.ms.client.character.Char;

public class Dragon extends Life {

    private Char owner;

    public Dragon(int templateId) {
        super(templateId);
    }

    public Char getOwner() {
        return owner;
    }

    public void setOwner(Char owner) {
        this.owner = owner;
    }

    public void resetToPlayer() {
        setPosition(owner.getPosition().deepCopy());
        setMoveAction((byte) 4); // default
    }

    @Override
    public void broadcastSpawnPacket(Char onlyChar) {
        getField().broadcastPacket(DragonPool.createDragon(this));
    }

    @Override
    public void broadcastLeavePacket() {
        getField().broadcastPacket(DragonPool.removeDragon(this));
    }
}
