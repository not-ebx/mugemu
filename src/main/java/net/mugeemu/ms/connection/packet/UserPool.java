package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.client.character.avatar.AvatarLook;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.character.CharacterStat;
import net.mugeemu.ms.life.pet.Pet;
import net.mugeemu.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.mugeemu.ms.client.character.skills.temp.TemporaryStatManager;
import net.mugeemu.ms.client.guild.Guild;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.enums.TSIndex;
import net.mugeemu.ms.handlers.header.OutHeader;

/**
 * Created on 3/18/2018.
 */
public class UserPool {
    public static OutPacket userEnterField(Char chr) {
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        AvatarLook al = chr.getAvatarData().getAvatarLook();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        OutPacket outPacket = new OutPacket(OutHeader.USER_ENTER_FIELD);

        outPacket.encodeInt(chr.getId());
        outPacket.encodeByte(chr.getLevel());
        outPacket.encodeString(chr.getName());
        //TODO: Ult Explorah =)
        outPacket.encodeString(""); // parent name, deprecated
        if(chr.getGuild() != null) {
            chr.getGuild().encodeForRemote(outPacket);
        } else {
            Guild.defaultEncodeForRemote(outPacket);
        }
        outPacket.encodeByte(cs.getGender());
        outPacket.encodeInt(cs.getPop());
        outPacket.encodeInt(0); // nNameTagMark
        tsm.encodeForRemote(outPacket, tsm.getCurrentStats());
        outPacket.encodeShort(chr.getJob());
        outPacket.encodeShort(cs.getSubJob());
        outPacket.encodeInt(chr.getTotalChuc());
        al.encode(outPacket);
        outPacket.encodeInt(chr.getDriverID());
        outPacket.encodeInt(chr.getPassengerID()); // dwPassenserID
        // new 176: sub_191E2D0
        //outPacket.encodeInt(0);
        //outPacket.encodeInt(0);
        int size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
        }
        // end sub_191E2D0
        outPacket.encodeInt(chr.getChocoCount());
        outPacket.encodeInt(chr.getActiveEffectItemID());
        outPacket.encodeInt(chr.getMonkeyEffectItemID());
        outPacket.encodeInt(chr.getActiveNickItemID());
        outPacket.encodeInt(chr.getDamageSkin().getDamageSkinID());
        outPacket.encodeInt(0); // ptPos.x?
        outPacket.encodeInt(al.getDemonWingID());
        outPacket.encodeInt(chr.getCompletedSetItemID());
        outPacket.encodeShort(chr.getFieldSeatID());
        outPacket.encodeInt(chr.getPortableChairID());
        boolean hasPortableChairMsg = chr.getPortableChairMsg() != null;
        outPacket.encodeInt(hasPortableChairMsg ? 1 : 0); // why is this an int
        if(hasPortableChairMsg) {
            outPacket.encodeString(chr.getPortableChairMsg());
        }
        int towerIDSize = 0;
        outPacket.encodeInt(towerIDSize);
        for (int i = 0; i < towerIDSize; i++) {
            outPacket.encodeInt(0); // towerChairID
        }
        outPacket.encodeInt(0); // some other position? new
        outPacket.encodeInt(0); // some other position? new
        outPacket.encodePosition(chr.getPosition());
        outPacket.encodeByte(chr.getMoveAction());
        outPacket.encodeShort(chr.getFoothold());
        outPacket.encodeByte(0); // ? new
        for(Pet pet : chr.getPets()) {
            if(pet.getId() == 0) {
                continue;
            }
            outPacket.encodeByte(1);
            outPacket.encodeInt(pet.getIdx());
            pet.encode(outPacket);
        }
        outPacket.encodeByte(0); // indicating that pets are no longer being encoded

        outPacket.encodeByte(0); // if true, encode something. idk what (v4->vfptr[35].Update)(v4, iPacket);
        outPacket.encodeByte(1); // new, having a 0 will 38
        outPacket.encodeByte(chr.getMechanicHue());
        outPacket.encodeInt(chr.getTamingMobLevel());
        outPacket.encodeInt(chr.getTamingMobExp());
        outPacket.encodeInt(chr.getTamingMobFatigue());
        byte miniRoomType = chr.getMiniRoom() != null ? chr.getMiniRoom().getType() : 0;
        outPacket.encodeByte(miniRoomType);
        if(miniRoomType > 0) {
            chr.getMiniRoom().encode(outPacket);
        }
        outPacket.encodeByte(chr.getADBoardRemoteMsg() != null);
        if (chr.getADBoardRemoteMsg() != null) {
            outPacket.encodeString(chr.getADBoardRemoteMsg());
        }
        outPacket.encodeByte(chr.isInCouple());
        if(chr.isInCouple()) {
            chr.getCouple().encodeForRemote(outPacket);
        }
        outPacket.encodeByte(chr.hasFriendshipItem());
        if(chr.hasFriendshipItem()) {
            chr.getFriendshipRingRecord().encode(outPacket);
        }
        outPacket.encodeByte(chr.isMarried());
        if(chr.isMarried()) {
            chr.getMarriageRecord().encodeForRemote(outPacket);
        }
        outPacket.encodeByte(0); // some flag that shows uninteresting things for now
        outPacket.encodeInt(chr.getEvanDragonGlide());
        if(JobConstants.isKaiser(chr.getJob())) {
            outPacket.encodeInt(chr.getKaiserMorphRotateHueExtern());
            outPacket.encodeInt(chr.getKaiserMorphPrimiumBlack());
            outPacket.encodeByte(chr.getKaiserMorphRotateHueInnner());
        }
        outPacket.encodeInt(chr.getMakingMeisterSkillEff());
        for (int i = 0; i < 5; i++) {
            outPacket.encodeByte(-1); // activeEventNameTag
        }
        outPacket.encodeInt(chr.getCustomizeEffect());
        if(chr.getCustomizeEffect() > 0) {
            outPacket.encodeString(chr.getCustomizeEffectMsg());
        }
        outPacket.encodeByte(chr.getSoulEffect());
        if(tsm.hasStat(CharacterTemporaryStat.RideVehicle)) {
            int vehicleID = tsm.getTSBByTSIndex(TSIndex.RideVehicle).getNOption();
            if(vehicleID == 1932249) { // is_mix_vehicle
                size = 0;
                outPacket.encodeInt(size); // ???
                for (int i = 0; i < size; i++) {
                    outPacket.encodeInt(0);
                }
            }
        }
        /*
         Flashfire (12101025) info
         not really interested in encoding this
         structure is:
         if(bool)
            if(bool)
                slv = int
                notused = int
                x = short
                y = short
         */
        outPacket.encodeByte(0);
        outPacket.encodeInt(0); // CUser::DecodeTextEquipInfo
        outPacket.encodeInt(chr.getEventBestFriendAID());
        // end kmst
        outPacket.encodeInt(0);
        outPacket.encodeInt(0);
        outPacket.encodeString("");
        outPacket.encodeInt(0);
        boolean bool = false;
        outPacket.encodeByte(bool);
        if(bool) {
            size = 0;
            outPacket.encodeInt(size);
            for (int i = 0; i < size; i++) {
                outPacket.encodeInt(0);
            }
        }
        int someID = 0;
        outPacket.encodeInt(someID);
        if(someID > 0) {
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeInt(0);
            outPacket.encodeShort(0);
            outPacket.encodeShort(0);
        }
        outPacket.encodeInt(0);
        // start sub_16D99C0
        size = 0;
        outPacket.encodeInt(size);
        for (int i = 0; i < size; i++) {
            outPacket.encodeInt(0);
        }
        // end sub_16D99C0
        return outPacket;
    }

    public static OutPacket userLeaveField(Char chr) {
        OutPacket outPacket = new OutPacket(OutHeader.USER_LEAVE_FIELD);

        outPacket.encodeInt(chr.getId());

        return outPacket;
    }
}
