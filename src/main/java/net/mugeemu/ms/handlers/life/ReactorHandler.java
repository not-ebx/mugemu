package net.mugeemu.ms.handlers.life;

import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.handlers.header.InHeader;
import net.mugeemu.ms.client.Client;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.handlers.Handler;
import net.mugeemu.ms.life.Life;
import net.mugeemu.ms.life.Reactor;
import net.mugeemu.ms.life.mob.Mob;
import net.mugeemu.ms.loaders.ReactorData;
import net.mugeemu.ms.loaders.containerclasses.ReactorInfo;
import net.mugeemu.ms.scripts.ScriptType;
import net.mugeemu.ms.world.field.Field;
import org.apache.log4j.Logger;

import javax.script.ScriptException;

public class ReactorHandler {

    private static final Logger log = Logger.getLogger(ReactorHandler.class);


    @Handler(op = InHeader.REACTOR_CLICK)
    public static void handleReactorClick(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int objID = inPacket.decodeInt();
        int idk = inPacket.decodeInt();
        byte type = inPacket.decodeByte();
        Life life = chr.getField().getLifeByObjectID(objID);
        if (!(life instanceof Reactor)) {
            log.error("Could not find reactor with objID " + objID);
            return;
        }
        Reactor reactor = (Reactor) life;
        int templateID = reactor.getTemplateId();
        ReactorInfo ri = ReactorData.getReactorInfoByID(templateID);
        String action = ri.getAction();
        if (chr.getScriptManager().isActive(ScriptType.Reactor)
                && chr.getScriptManager().getParentIDByScriptType(ScriptType.Reactor) == templateID) {
            try {
                chr.getScriptManager().getInvocableByType(ScriptType.Reactor).invokeFunction("action", reactor, type);
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            chr.getScriptManager().startScript(templateID, objID, action, ScriptType.Reactor);
        }
    }

    @Handler(op = InHeader.REACTOR_RECT_IN_MOB)
    public static void handleReactorRectInMob(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int objID = inPacket.decodeInt();

        Life life = chr.getField().getLifeByObjectID(objID);
        if (!(life instanceof Reactor)) {
            log.error("Could not find reactor with objID " + objID);
            return;
        }
        Reactor reactor = (Reactor) life;
        int templateID = reactor.getTemplateId();
        ReactorInfo ri = ReactorData.getReactorInfoByID(templateID);
        String action = ri.getAction();
        if (action.equals("")) {
            action = templateID + "action";
        }
        if (chr.getScriptManager().isActive(ScriptType.Reactor)
                && chr.getScriptManager().getParentIDByScriptType(ScriptType.Reactor) == templateID) {
            try {
                chr.getScriptManager().getInvocableByType(ScriptType.Reactor).invokeFunction("action", 0);
            } catch (ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            chr.getScriptManager().startScript(templateID, objID, action, ScriptType.Reactor);
        }
    }

    @Handler(op = InHeader.REACTOR_KEY)
    public static void handleReactorKey(Client c, InPacket inPacket) {
        Char chr = c.getChr();
        int objID = inPacket.decodeInt();
        int lifeID = inPacket.decodeInt();
        Mob mob = (Mob) chr.getField().getLifeByObjectID(lifeID);
        mob.die(false);
        Field field = chr.getField();
        for (Char character : field.getChars()) {
            character.increaseGolluxStack();
        }
    }

}
