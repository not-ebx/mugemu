package net.mugeemu.ms.handlers;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.packet.Effect;
import net.mugeemu.ms.connection.packet.FieldPacket;
import net.mugeemu.ms.connection.packet.UserPacket;
import net.mugeemu.ms.connection.packet.UserRemote;
import net.mugeemu.ms.handlers.header.InHeader;
import net.mugeemu.ms.client.character.Char;
import org.apache.log4j.Logger;

public class FieldHandler {

    private static final Logger log = Logger.getLogger(FieldHandler.class);

    @Handler(op = InHeader.BROADCAST_EFFECT_TO_SPLIT)
    public static void handleBroadcastEffectToSplit(Char chr, InPacket inPacket) {
        String effectPath = inPacket.decodeString();
        int duration = inPacket.decodeInt();
        int option = inPacket.decodeInt();
        chr.write(UserPacket.effect(Effect.effectFromWZ(effectPath, true, duration, option, 0)));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.effectFromWZ(effectPath, true, duration, option, 0)), chr);
    }

    @Handler(op = InHeader.BROADCAST_ONE_TIME_ACTION_TO_SPLIT)
    public static void handleBroadcastOneTimeActionToSplit(Char chr, InPacket inPacket) {
        int action = inPacket.decodeInt();
        int duration = inPacket.decodeInt();
        chr.getField().broadcastPacket(FieldPacket.setOneTimeAction(chr.getId(), action, duration));
    }
}
