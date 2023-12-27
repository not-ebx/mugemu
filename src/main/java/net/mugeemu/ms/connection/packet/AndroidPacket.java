package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.handlers.header.OutHeader;
import net.mugeemu.ms.life.Android;
import net.mugeemu.ms.life.movement.MovementInfo;

/**
 * @author Sjonnie
 * Created on 2/12/2019.
 */
public class AndroidPacket {

    public static OutPacket created(Android android) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_CREATED);

        outPacket.encodeInt(android.getChrId());
        outPacket.encode(android);

        return outPacket;
    }

    public static OutPacket removed(Android android) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_REMOVED);

        outPacket.encodeInt(android.getChrId());

        return outPacket;
    }

    public static OutPacket move(Android android, MovementInfo mi) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_MOVE);

        outPacket.encodeInt(android.getChrId());
        mi.encode(outPacket);

        return outPacket;
    }

    public static OutPacket actionSet(Android android, int action, int randomKey) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_ACTION_SET);

        outPacket.encodeInt(android.getChrId());
        outPacket.encodeByte(action);
        outPacket.encodeByte(randomKey);

        return outPacket;
    }

    public static OutPacket modified(Android android) {
        OutPacket outPacket = new OutPacket(OutHeader.ANDROID_MODIFIED);

        outPacket.encodeInt(android.getChrId());

        // from the right: 1st 7 bits for each equip, 8th bit for face+eye+hair+name
        // 0xFF is a full update
        outPacket.encodeByte(0xFF);
        for (int itemId : android.getItems()) {
            outPacket.encodeInt(itemId);
        }
        android.encodeAndroidInfo(outPacket);

        return outPacket;
    }
}
