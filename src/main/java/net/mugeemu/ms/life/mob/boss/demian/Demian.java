package net.mugeemu.ms.life.mob.boss.demian;

import net.mugeemu.ms.connection.packet.DemianFieldPacket;
import net.mugeemu.ms.constants.BossConstants;
import net.mugeemu.ms.handlers.EventManager;
import net.mugeemu.ms.life.mob.boss.demian.stigma.DemianStigma;
import net.mugeemu.ms.life.mob.boss.demian.stigma.DemianStigmaIncinerateObject;
import net.mugeemu.ms.world.field.Field;
import net.mugeemu.ms.client.character.Char;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created on 17-8-2019.
 *
 * @author Asura
 */
public class Demian {

    public static ScheduledFuture stigmaIncinerateObjectTimer(Field field) {
        DemianStigmaIncinerateObject o = new DemianStigmaIncinerateObject(-1);
        ScheduledFuture sf = EventManager.addFixedRateEvent(() -> field.spawnLifeForTime(o, BossConstants.DEMIAN_STIGMA_INCINERATE_OBJECT_DURATION_TIME), 5000, BossConstants.DEMIAN_STIGMA_INCINERATE_OBJECT_RESPAWN_TIME, TimeUnit.MILLISECONDS);
        return sf;
    }

    public static ScheduledFuture increaseStigmaPassiveTimer(Char chr) {
        ScheduledFuture sf = EventManager.addFixedRateEvent(() -> increaseStigmaPassive(chr), 0, BossConstants.DEMIAN_PASSIVE_STIGMA_TIME, TimeUnit.MILLISECONDS);
        return sf;
    }

    public static void increaseStigmaPassive(Char chr) {
        DemianStigma.incStigma(chr);
        chr.write(DemianFieldPacket.stigmaRemainTime(BossConstants.DEMIAN_PASSIVE_STIGMA_TIME));
    }
}
