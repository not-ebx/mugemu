package net.mugeemu.ms.client.character.avatar;

import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.client.character.CharacterStat;

import jakarta.persistence.*;
import net.mugeemu.ms.connection.db.daos.UserDAO;

/**
 * Created on 2/18/2017.
 */
@Entity
@Table(name = "avatardata")
public class AvatarData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "characterStat")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CharacterStat characterStat;
    @JoinColumn(name = "avatarLook")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private AvatarLook avatarLook;
    @JoinColumn(name = "zeroAvatarLook")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private AvatarLook zeroAvatarLook;

    @OneToOne(mappedBy = "avatarData", fetch = FetchType.LAZY)
    private Char character;

    public AvatarLook getAvatarLook() {
        return avatarLook;
    }

    public CharacterStat getCharacterStat() {
        return characterStat;
    }

    public AvatarLook getZeroAvatarLook() {
        return zeroAvatarLook;
    }

    public void setZeroAvatarLook(AvatarLook zeroAvatarLook) {
        this.zeroAvatarLook = zeroAvatarLook;
    }

    public void encode(OutPacket outPacket) {
        // Reload char data
        getCharacterStat().encode(outPacket, this.avatarLook.getPetIDs());
        getAvatarLook().encode(outPacket);
    }

    public void setCharacterStat(CharacterStat characterStat) {
        this.characterStat = characterStat;
    }

    public void setAvatarLook(AvatarLook avatarLook) {
        this.avatarLook = avatarLook;
    }

    public int getId() {
        return id;
    }

    public int getCharacterId() {
        return this.getCharacter().getId();
    }

    public Char getCharacter() {
        if (this.character != null)
            return this.character;
        UserDAO udao = new UserDAO();
        this.character = udao.getCharacterByAvatarData(this);
        return this.character;
    }

    public void setCharacter(Char chara) {
        this.character = chara;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AvatarLook getAvatarLook(boolean zeroBetaState) {
        return getAvatarLook();
    }
}
