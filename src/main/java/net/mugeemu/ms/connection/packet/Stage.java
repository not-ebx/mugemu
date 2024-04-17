package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.client.character.damage.DamageCalc;
import net.mugeemu.ms.util.FileTime;
import net.mugeemu.ms.world.field.Field;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.world.field.FieldCustom;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.enums.DBChar;
import net.mugeemu.ms.handlers.header.OutHeader;
import net.mugeemu.ms.world.shop.cashshop.CashShop;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created on 12/14/2017.
 */
public class Stage {

    public static OutPacket setField(Char chr, Field field, int channelId, boolean dev, int oldDriverID,
                                     boolean characterData, boolean usingBuffProtector, byte portal,
                                     boolean setWhiteFadeInOut, int mobStatAdjustRate, FieldCustom fieldCustom,
                                     boolean canNotifyAnnouncedQuest, int stackEventGauge) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_FIELD);

        // Opt code thing
        outPacket.encodeShort(0);
        outPacket.encodeInt(channelId - 1);
        outPacket.encodeInt(oldDriverID);
        outPacket.encodeByte(0); // sNotifierMessage
        outPacket.encodeByte(characterData);
        short notifierCheck = 0;
        outPacket.encodeShort(notifierCheck); // Notifier Check
        // This won't be used but i keep it here for reference. :D
        if (notifierCheck != 0) {
            outPacket.encodeString(""); //pChatBlockReason
            if(notifierCheck > 0) {
                for (short i = 0; i < notifierCheck; i++) {
                    outPacket.encodeString(""); //sMsg2 Str
                }
            }
        }

        if (characterData) {
            Random random = new SecureRandom();

            int s1 = random.nextInt();
            int s2 = random.nextInt();
            int s3 = random.nextInt();

            outPacket.encodeInt(s1);
            outPacket.encodeInt(s2);
            outPacket.encodeInt(s3);
            chr.setDamageCalc(new DamageCalc(chr, s1, s2, s3));


            chr.encode(outPacket, DBChar.All);

            // Logout event. useless
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);

        } else {
            outPacket.encodeByte(usingBuffProtector);
            outPacket.encodeInt(field.getId());
            outPacket.encodeByte(portal);
            outPacket.encodeInt(chr.getAvatarData().getCharacterStat().getHp());
            boolean chaseEnable = false; // Chase Enable, (if is following??)
            outPacket.encodeByte(chaseEnable);

            if(chaseEnable) {
                outPacket.encodeInt(0); //x
                outPacket.encodeInt(0); //y
            }
        }

        outPacket.encodeFT(FileTime.currentTime());

        return outPacket;
    }

    private static void encodeLogoutEvent(OutPacket outPacket) {
        int idOrSomething = 0;
        outPacket.encodeInt(idOrSomething);
        if(idOrSomething > 0) {
            for (int i = 0; i < 3; i++) {
                // sub_9896B0
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeString("");
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeLong(0);
                outPacket.encodeLong(0);
                outPacket.encodeLong(0);
                outPacket.encodeLong(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeShort(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeString("");
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeInt(0);
                outPacket.encodeByte(0);
                // if(a3 & 1 != 0) -> encode int + str + buf of size 0x18 (24). a3 is 0 when called from setField
                int size = 0;
                outPacket.encodeInt(size);
                for (int j = 0; j < size; j++) {
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                    outPacket.encodeInt(0);
                }
            }
        }
    }

    public static OutPacket setCashShop(Char chr, CashShop cashShop) {
        OutPacket outPacket = new OutPacket(OutHeader.SET_CASH_SHOP);

        chr.encode(outPacket, DBChar.All);
        cashShop.encode(outPacket);

        return outPacket;
    }
}
