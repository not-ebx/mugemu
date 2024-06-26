package net.mugeemu.ms.client.character.potential;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.loaders.SkillData;
import net.mugeemu.ms.client.character.skills.Skill;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Class containg the properties of a single character potential.
 * Note that this class is equal to another instance iff the <code>key</code> properties are equal!
 * Created on 5/27/2018.
 */
@Entity
@Table(name = "characterpotentials")
public class CharacterPotential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "potKey")
    private byte key;
    private int skillID;
    private byte slv;
    private byte grade;

    public CharacterPotential() {
    }

    public CharacterPotential(byte key, int skillID, byte slv, byte grade) {
        this.key = key;
        this.skillID = skillID;
        this.slv = slv;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getKey() {
        return key;
    }

    public void setKey(byte key) {
        this.key = key;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public byte getSlv() {
        return slv;
    }

    public void setSlv(byte slv) {
        this.slv = slv;
    }

    public byte getGrade() {
        return grade;
    }

    public void setGrade(byte grade) {
        this.grade = grade;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getKey());
        outPacket.encodeInt(getSkillID());
        outPacket.encodeByte(getSlv());
        outPacket.encodeByte(getGrade());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterPotential that = (CharacterPotential) o;
        return key == that.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public Skill getSkill() {
        Skill skill = SkillData.getSkillDeepCopyById(getSkillID());
        skill.setCurrentLevel(getSlv());
        return skill;
    }
}
