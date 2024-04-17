package net.mugeemu.ms.client.character;

import net.mugeemu.ms.client.character.avatar.AvatarData;
import net.mugeemu.ms.client.character.cards.CharacterCard;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.connection.db.FileTimeConverter;
import net.mugeemu.ms.constants.GameConstants;
import net.mugeemu.ms.constants.JobConstants;
import net.mugeemu.ms.util.SystemTime;

import net.mugeemu.ms.util.FileTime;

import jakarta.persistence.*;

import java.util.List;

/**
 * Created by Tim on 2/18/2017.
 */
@Entity
@Table(name = "characterstats")
public class CharacterStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "characterStat", orphanRemoval = true, fetch = FetchType.LAZY)
    private AvatarData avatarData;

    private int characterIdForLog;
    private int worldIdForLog;

    @Column(unique = true)
    private String name;
    private int gender;
    private int skin;
    private int face;
    private int hair;
    private int mixBaseHairColor;
    private int mixAddHairColor;
    private int mixHairBaseProb;
    private int level;
    private int job;
    private int str;
    private int dex;
    private int inte;
    private int luk;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int ap;
    private int sp;
    private long exp;
    private int pop; // fame
    private long money;
    private int wp;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "extendSP")
    private ExtendSP extendSP;
    private long posMap;
    private int portal;
    private int subJob;
    private int defFaceAcc;
    private int fatigue;
    private int lastFatigueUpdateTime;
    private int charismaExp;
    private int insightExp;
    private int willExp;
    private int craftExp;
    private int senseExp;
    private int charmExp;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "nonCombatStatDayLimit")
    private NonCombatStatDayLimit nonCombatStatDayLimit;
    private int pvpExp;
    private int pvpGrade;
    private int pvpPoint;
    private int pvpModeLevel;
    private int pvpModeType;
    private int eventPoint;
    private int albaActivityID;
    @Convert(converter = FileTimeConverter.class)
    private FileTime albaStartTime;
    private int albaDuration;
    private int albaSpecialReward;
    private boolean burning;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "characterCard")
    private CharacterCard characterCard;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accountLastLogout")
    private SystemTime accountLastLogout;
    @Convert(converter = FileTimeConverter.class)
    private FileTime lastLogout;
    private int gachExp;
    private int honorExp;
    @Transient
    private int wingItem;
    @Convert(converter = FileTimeConverter.class)
    private FileTime nextAvailableFameTime;

    public CharacterStat() {
        extendSP = new ExtendSP(7);
        nonCombatStatDayLimit = new NonCombatStatDayLimit();
        albaStartTime = FileTime.fromType(FileTime.Type.PLAIN_ZERO);
        lastLogout = FileTime.fromType(FileTime.Type.PLAIN_ZERO);
        characterCard = new CharacterCard(0, 0, (byte) 0);
        accountLastLogout = new SystemTime(1970, 1);
        nextAvailableFameTime = FileTime.fromType(FileTime.Type.PLAIN_ZERO);
        // TODO fill in default vals
    }

    public CharacterStat(String name, int job) {
        this();
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public short getAp() {
        return (short) ap;
    }

    public short getDex() {
        return (short) dex;
    }

    public int getHp() {
        return hp;
    }

    public short getInt() {
        return (short) inte;
    }

    public short getJob() {
        return (short) job;
    }

    public short getLevel() {
        return (short) level;
    }

    public int getCharismaExp() {
        return charismaExp;
    }

    public short getLuk() {
        return (short) luk;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public int getMp() {
        return mp;
    }

    public short getPop() { //Fame
        return (short) pop;
    }

    public short getSp() {
        return (short) sp;
    }

    public short getStr() {
        return (short) str;
    }

    public short getWp() {
        return (short) wp;
    }

    public long getExp() {
        return exp;
    }

    public long getMoney() {
        return money;
    }

    public ExtendSP getExtendSP() {
        return extendSP;
    }

    public int getCharacterId() {

        return this.avatarData.getCharacterId();
    }

    public int getCharacterIdForLog() {
        return this.getCharacterId();
    }

    public AvatarData getAvatarData() {
        return this.avatarData;
    }

    public void setAvatarData(AvatarData avatarData) {
        this.avatarData = avatarData;
    }

    public int getFace() {
        return face;
    }

    public int getGender() {
        return gender;
    }

    public int getHair() {
        return hair;
    }

    public int getMixAddHairColor() {
        return mixAddHairColor;
    }

    public int getMixBaseHairColor() {
        return mixBaseHairColor;
    }

    public int getMixHairBaseProb() {
        return mixHairBaseProb;
    }

    public int getSkin() {
        return skin;
    }

    public int getWorldIdForLog() {
        return worldIdForLog;
    }

    public int getCharmExp() {
        return charmExp;
    }

    public int getCraftExp() {
        return craftExp;
    }

    public int getAlbaActivityID() {
        return albaActivityID;
    }

    public int getEventPoint() {
        return eventPoint;
    }

    public int getPortal() {
        return portal;
    }

    public int getAlbaDuration() {
        return albaDuration;
    }

    public int getInsightExp() {
        return insightExp;
    }

    public int getAlbaSpecialReward() {
        return albaSpecialReward;
    }

    public int getPvpExp() {
        return pvpExp;
    }

    public int getPvpGrade() {
        return pvpGrade;
    }

    public int getPvpModeLevel() {
        return pvpModeLevel;
    }

    public int getPvpModeType() {
        return pvpModeType;
    }

    public int getPvpPoint() {
        return pvpPoint;
    }

    public int getSenseExp() {
        return senseExp;
    }

    public int getWillExp() {
        return willExp;
    }

    public long getPosMap() {
        return posMap == 0 ? 931000000 : posMap;
    }

    public CharacterCard getCharacterCard() {
        return characterCard;
    }

    public NonCombatStatDayLimit getNonCombatStatDayLimit() {
        return nonCombatStatDayLimit;
    }

    public FileTime getAlbaStartTime() {
        return albaStartTime;
    }

    public int getDefFaceAcc() {
        return defFaceAcc;
    }

    public int getFatigue() {
        return fatigue;
    }

    public int getLastFatigueUpdateTime() {
        return lastFatigueUpdateTime;
    }

    public int getSubJob() {
        return subJob;
    }

    public SystemTime getAccountLastLogout() {
        return accountLastLogout;
    }

    public void encode(OutPacket outPacket, List<Integer> petIds) {

        outPacket.encodeInt(getCharacterId());
        outPacket.encodeString(getName(), 13);
        outPacket.encodeByte(getGender());
        outPacket.encodeByte(getSkin());
        outPacket.encodeInt(getFace());
        outPacket.encodeInt(getHair());
        for (int i = 0; i < 3; i++) {
            if (petIds.size() > i) {
                outPacket.encodeLong(petIds.get(i));
            } else {
                outPacket.encodeLong(0);
            }
        }
        outPacket.encodeByte(getLevel());
        outPacket.encodeShort(getJob());
        outPacket.encodeShort(getStr());
        outPacket.encodeShort(getDex());
        outPacket.encodeShort(getInt());
        outPacket.encodeShort(getLuk());
        outPacket.encodeShort(getHp());
        outPacket.encodeShort(getMaxHp());
        outPacket.encodeShort(getMp());
        outPacket.encodeShort(getMaxMp());
        outPacket.encodeShort(getAp());
        // TODO check this, ahvent tested
        if (JobConstants.isExtendSpJob(getJob())) {
            getExtendSP().encode(outPacket);
        } else {
            //getExtendSP().encode(outPacket);
            outPacket.encodeShort(getSp());
        }

        outPacket.encodeInt((int)getExp()); // Long in numaple!
        outPacket.encodeShort(getPop());
        outPacket.encodeInt(getGachExp());
        outPacket.encodeInt((int)getPosMap());
        outPacket.encodeByte(getPortal());
        outPacket.encodeInt(0); // TODO figure out -> Online time in seconds wtf
        outPacket.encodeShort(getSubJob());

    }

    public FileTime getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(FileTime lastLogout) {
        this.lastLogout = lastLogout;
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGachExp() {
        return gachExp;
    }

    public void setCharacterIdForLog(int characterIdForLog) {
        this.characterIdForLog = characterIdForLog;
    }

    public void setWorldIdForLog(int worldIdForLog) {
        this.worldIdForLog = worldIdForLog;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setMixAddHairColor(int mixAddHairColor) {
        this.mixAddHairColor = mixAddHairColor;
    }

    public void setMixHairBaseProb(int mixHairBaseProb) {
        this.mixHairBaseProb = mixHairBaseProb;
    }

    public void setMixBaseHairColor(int mixBaseHairColor) {
        this.mixBaseHairColor = mixBaseHairColor;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public void setInt(int inte) {
        this.inte = inte;
    }

    public void setLuk(int luk) {
        this.luk = luk;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, GameConstants.MAX_HP_MP);
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = Math.min(maxHp, GameConstants.MAX_HP_MP);
    }

    public void setMp(int mp) {
        this.mp = Math.min(mp, GameConstants.MAX_HP_MP);
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = Math.min(maxMp, GameConstants.MAX_HP_MP);
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public void setSp(int sp) {
        this.sp = sp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setWp(int wp) {
        this.wp = wp;
    }

    public void setPosMap(long posMap) {
        this.posMap = posMap;
    }

    public void setPortal(int portal) {
        this.portal = portal;
    }

    public void setSubJob(int subJob) {
        this.subJob = subJob;
    }

    public void setDefFaceAcc(int defFaceAcc) {
        this.defFaceAcc = defFaceAcc;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public void setLastFatigueUpdateTime(int lastFatigueUpdateTime) {
        this.lastFatigueUpdateTime = lastFatigueUpdateTime;
    }

    public void setCharismaExp(int charismaExp) {
        this.charismaExp = charismaExp;
    }

    public void setInsightExp(int insightExp) {
        this.insightExp = insightExp;
    }

    public void setWillExp(int willExp) {
        this.willExp = willExp;
    }

    public void setCraftExp(int craftExp) {
        this.craftExp = craftExp;
    }

    public void setSenseExp(int senseExp) {
        this.senseExp = senseExp;
    }

    public void setCharmExp(int charmExp) {
        this.charmExp = charmExp;
    }

    public void setPvpExp(int pvpExp) {
        this.pvpExp = pvpExp;
    }

    public void setPvpGrade(int pvpGrade) {
        this.pvpGrade = pvpGrade;
    }

    public void setPvpPoint(int pvpPoint) {
        this.pvpPoint = pvpPoint;
    }

    public void setPvpModeLevel(int pvpModeLevel) {
        this.pvpModeLevel = pvpModeLevel;
    }

    public void setPvpModeType(int pvpModeType) {
        this.pvpModeType = pvpModeType;
    }

    public void setEventPoint(int eventPoint) {
        this.eventPoint = eventPoint;
    }

    public void setAlbaActivityID(int albaActivityID) {
        this.albaActivityID = albaActivityID;
    }

    public void setAlbaDuration(int albaDuration) {
        this.albaDuration = albaDuration;
    }

    public void setAlbaSpecialReward(int albaSpecialReward) {
        this.albaSpecialReward = albaSpecialReward;
    }

    public void setGachExp(int gachExp) {
        this.gachExp = gachExp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExtendSP(ExtendSP extendSP) {
        this.extendSP = extendSP;
    }

    public void setNonCombatStatDayLimit(NonCombatStatDayLimit nonCombatStatDayLimit) {
        this.nonCombatStatDayLimit = nonCombatStatDayLimit;
    }

    public void setAlbaStartTime(FileTime albaStartTime) {
        this.albaStartTime = albaStartTime;
    }

    public void setCharacterCard(CharacterCard characterCard) {
        this.characterCard = characterCard;
    }

    public void setAccountLastLogout(SystemTime accountLastLogout) {
        this.accountLastLogout = accountLastLogout;
    }

    public int getHonorExp() {
        return honorExp;
    }

    public void setHonorExp(int honorExp) {
        this.honorExp = honorExp;
    }

    public void setWingItem(int wingItem) {
        this.wingItem = wingItem;
    }

    public int getWingItem() {
        return wingItem;
    }

    public FileTime getNextAvailableFameTime() {
        return nextAvailableFameTime;
    }

    public void setNextAvailableFameTime(FileTime nextAvailableFameTime) {
        this.nextAvailableFameTime = nextAvailableFameTime;
    }
}

