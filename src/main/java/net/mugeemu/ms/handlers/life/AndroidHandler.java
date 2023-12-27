package net.mugeemu.ms.handlers.life;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.packet.AndroidPacket;
import net.mugeemu.ms.handlers.header.InHeader;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.handlers.Handler;
import net.mugeemu.ms.life.Android;
import net.mugeemu.ms.life.movement.MovementInfo;
import org.apache.log4j.Logger;

public class AndroidHandler {

    private static final Logger log = Logger.getLogger(AndroidHandler.class);

    @Handler(op = InHeader.ANDROID_MOVE)
    public static void handleAndroidMove(Char chr, InPacket inPacket) {
        Android android = chr.getAndroid();
        if (android == null) {
            return;
        }
        MovementInfo mi = new MovementInfo(inPacket);
        mi.applyTo(android);
        chr.getField().broadcastPacket(AndroidPacket.move(android, mi), chr);
    }
}
