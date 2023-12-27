package net.mugeemu.ms.handlers.life;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.packet.DragonPool;
import net.mugeemu.ms.handlers.header.InHeader;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.handlers.Handler;
import net.mugeemu.ms.life.Dragon;
import net.mugeemu.ms.life.movement.MovementInfo;
import org.apache.log4j.Logger;

public class DragonHandler {

    private static final Logger log = Logger.getLogger(DragonHandler.class);


    @Handler(op = InHeader.DRAGON_MOVE)
    public static void handleDragonMove(Char chr, InPacket inPacket) {
        Dragon dragon = chr.getDragon();
        if (dragon != null && dragon.getOwner() == chr) {
            MovementInfo movementInfo = new MovementInfo(inPacket);
            movementInfo.applyTo(dragon);
            chr.getField().broadcastPacket(DragonPool.moveDragon(dragon, movementInfo), chr);
        }
    }
}
