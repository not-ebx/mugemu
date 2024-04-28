package net.mugeemu.ms.client.character;


import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.connection.db.FileTimeConverter;
import net.mugeemu.ms.util.FileTime;

import jakarta.persistence.*;

/**
 * Created on 2/18/2017.
 */
@Entity
@Table(name = "noncombatstatdaylimit")
public class NonCombatStatDayLimit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private short charisma;
    private short charm;
    private short insight;
    private short will;
    private short craft;
    private short sense;
    @Convert(converter = FileTimeConverter.class)
    private FileTime lastUpdateCharmByCashPR;
    private byte charmByCashPR;

    public NonCombatStatDayLimit(short charisma, short charm, byte charmByCashPR, short insight, short will, short craft, short sense, FileTime lastUpdateCharmByCashPR) {
        this.charisma = charisma;
        this.charm = charm;
        this.charmByCashPR = charmByCashPR;
        this.insight = insight;
        this.will = will;
        this.craft = craft;
        this.sense = sense;
        this.lastUpdateCharmByCashPR = lastUpdateCharmByCashPR;
    }

    public NonCombatStatDayLimit() {
        this((short) 0, (short) 0, (byte) 0,(short) 0,(short) 0,(short) 0,(short) 0, FileTime.fromType(FileTime.Type.ZERO_TIME));
    }

    public short getCharm() {
        return charm;
    }

    public void setCharm(short charm) {
        this.charm = charm;
    }

    public byte getCharmByCashPR() {
        return charmByCashPR;
    }

    public void setCharmByCashPR(byte charmByCashPR) {
        this.charmByCashPR = charmByCashPR;
    }

    public short getInsight() {
        return insight;
    }

    public void setInsight(short insight) {
        this.insight = insight;
    }

    public short getWill() {
        return will;
    }

    public void setWill(short will) {
        this.will = will;
    }

    public short getCraft() {
        return craft;
    }

    public void setCraft(short craft) {
        this.craft = craft;
    }

    public short getSense() {
        return sense;
    }

    public void setSense(short sense) {
        this.sense = sense;
    }

    public FileTime getLastUpdateCharmByCashPR() {
        return lastUpdateCharmByCashPR;
    }

    public void setLastUpdateCharmByCashPR(FileTime lastUpdateCharmByCashPR) {
        this.lastUpdateCharmByCashPR = lastUpdateCharmByCashPR;
    }

    public void encode(OutPacket outPacket) {
        // 2 4 6 7 10 12 // End of buffer.
        //outPacket.encodeShort(getCharisma()); // 2
        //outPacket.encodeShort(getInsight()); // 4
        //outPacket.encodeShort(getWill()); // 6
        //outPacket.encodeShort(getCraft()); // 8
        //outPacket.encodeShort(getSense()); // 10
        //outPacket.encodeShort(getCharm()); // 12
        // TODO
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
        outPacket.encodeShort(0);
        //outPacket.encodeByte(getCharmByCashPR());
        //outPacket.encodeFT(getLastUpdateCharmByCashPR());
    }

    public short getCharisma() {
        return charisma;
    }

    public void setCharisma(short charisma) {
        this.charisma = charisma;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
