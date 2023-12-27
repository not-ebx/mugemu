package net.mugeemu.ms.handlers.life;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.handlers.header.InHeader;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.handlers.Handler;
import net.mugeemu.ms.world.field.Field;
import org.apache.log4j.Logger;

public class AffectedAreaHandler {

    private static final Logger log = Logger.getLogger(AffectedAreaHandler.class);


    @Handler(op = InHeader.USER_AFFECTED_AREA_REMOVE_BY_TIME)
    public static void handleUserAffectedAreaRemoveByTime(Char chr, InPacket inPacket) {
        int skillID = inPacket.decodeInt();

        Field field = chr.getField();
        field.getAffectedAreas().stream().filter(aa -> aa.getOwner() == chr && aa.getSkillID() == skillID).findFirst().ifPresent(field::removeLife);
    }
}
