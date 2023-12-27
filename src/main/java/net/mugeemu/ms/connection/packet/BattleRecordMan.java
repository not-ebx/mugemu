package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.handlers.header.OutHeader;
import net.mugeemu.ms.life.mob.skill.BurnedInfo;
import net.mugeemu.ms.util.Util;

/**
 * Created on 2/10/2018.
 */
public class BattleRecordMan {
    public static OutPacket serverOnCalcRequestResult(boolean on) {
        OutPacket outPacket = new OutPacket(OutHeader.SERVER_ON_CALC_REQUEST_RESULT);

        outPacket.encodeByte(true);

        return outPacket;
    }

    public static OutPacket dotDamageInfo(BurnedInfo burnedInfo, int count) {
        OutPacket outPacket = new OutPacket(OutHeader.DOT_DAMAGE_INFO.getValue());

        outPacket.encodeInt(Util.maxInt(burnedInfo.getDamage()));
        outPacket.encodeInt(count);

        outPacket.encodeByte(burnedInfo.getDotTickDamR() > 0);
        if (burnedInfo.getDotTickDamR() > 0) {
            outPacket.encodeInt(burnedInfo.getDotTickDamR());
        }
        outPacket.encodeInt(burnedInfo.getSkillId());

        return outPacket;
    }
}
