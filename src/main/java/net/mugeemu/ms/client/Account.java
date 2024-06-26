package net.mugeemu.ms.client;

import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.character.MonsterCollection;
import net.mugeemu.ms.client.character.damage.DamageSkinSaveData;
import net.mugeemu.ms.client.friend.Friend;
import net.mugeemu.ms.connection.db.DatabaseManager;
import net.mugeemu.ms.connection.db.daos.UserDAO;
import net.mugeemu.ms.constants.ItemConstants;
import net.mugeemu.ms.constants.SkillConstants;
import net.mugeemu.ms.life.Merchant.EmployeeTrunk;
import net.mugeemu.ms.loaders.StringData;
import net.mugeemu.ms.util.Util;
import net.mugeemu.ms.client.trunk.Trunk;
import org.apache.log4j.Logger;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class representing an Account, which is a world-specific "User" class.
 *
 * Created by Tim on 4/30/2017.
 */
@Entity
@Table(name = "accounts")
public class Account {
    @Transient
    private static final Logger log = Logger.getLogger(Account.class);

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int worldId;
    @JoinColumn(name = "trunkID")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Trunk trunk;
    @JoinColumn(name = "employeetrunkID")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeeTrunk employeeTrunk;
    @JoinColumn(name = "monsterCollectionID")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private MonsterCollection monsterCollection;
    // no eager -> sometimes get a "resultset closed" when fetching friends/damage skins
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "owneraccid")
    private Set<Friend> friends;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "accID")
    private Set<DamageSkinSaveData> damageSkins = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Char> characters = new HashSet<>();

    private int nxCredit;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "accID")
    private Set<LinkSkill> linkSkills = new HashSet<>();
    //@Transient

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @Transient
    private Char currentChr;

    public Account(User user, int worldId) {
        this.user = user;
        this.worldId = worldId;
        this.trunk = new Trunk((byte) 4);
        this.monsterCollection = new MonsterCollection();
        this.friends = new HashSet<>();
        this.damageSkins = new HashSet<>();
        this.characters = new HashSet<>();
        this.linkSkills = new HashSet<>();
    }

    public Account(){
    }

    public static Account getFromDBById(int accountID) {
        UserDAO udao = new UserDAO();
        return udao.getAccountById(accountID);
    }

    public int getId() {
        return id;
    }

    public List<Char> getCharacters() {
        if(this.characters != null || !Hibernate.isPropertyInitialized(this, "characters"))
            return this.characters.stream().toList();

        UserDAO udao = new UserDAO();
        List<Char> myChara = udao.getCharactersByAccount(this);
        this.characters = new HashSet<>(myChara);
        return myChara;
    }

    public void addCharacter(Char character) {
       //getCharacters().add(character);
        this.characters.add(character);
       character.setAccount(this);
    }


    public void setId(int id) {
        this.id = id;
    }

    public Set<Friend> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friend> friends) {
        this.friends = friends;
    }

    public void addFriend(Friend friend) {
        if(getFriendByAccID(friend.getFriendAccountID()) == null) {
            getFriends().add(friend);
        }
    }

    public Friend getFriendByAccID(int accID) {
        return getFriends().stream().filter(f -> f.getFriendAccountID() == accID).findAny().orElse(null);
    }

    public void removeFriend(int accID) {
        removeFriend(getFriendByAccID(accID));
    }

    public void removeFriend(Friend f) {
        if(f != null) {
            getFriends().remove(f);
        }
    }

    public Set<DamageSkinSaveData> getDamageSkins() {
        return damageSkins;
    }

    public void setDamageSkins(Set<DamageSkinSaveData> damageSkins) {
        this.damageSkins = damageSkins;
    }

    public void addDamageSkin(DamageSkinSaveData dssd) {
        if(getDamageSkinByItemID(dssd.getItemID()) == null) {
            getDamageSkins().add(dssd);
        }
    }

    public void removeDamageSkin(DamageSkinSaveData dssd) {
        if(dssd != null) {
            getDamageSkins().remove(dssd);
        }
    }

    public void removeDamageSkin(int itemID) {
        removeDamageSkin(getDamageSkinByItemID(itemID));
    }

    public void addDamageSkinByItemID(int itemID) {
        addDamageSkin(new DamageSkinSaveData(
            ItemConstants.getDamageSkinIDByItemID(itemID), itemID, false,
                StringData.getItemStringById(itemID)));
    }

    public DamageSkinSaveData getDamageSkinByItemID(int itemID) {
        return getDamageSkins().stream().filter(d -> d.getItemID() == itemID).findAny().orElse(null);
    }

    public DamageSkinSaveData getDamageSkinBySkinID(int skinID) {
        return getDamageSkins().stream().filter(d -> d.getDamageSkinID() == skinID).findAny().orElse(null);
    }

    public Trunk getTrunk() {
        if(trunk == null) {
            trunk = new Trunk((byte) 20);
        }
        return trunk;
    }

    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }

    public int getNxCredit() {
        return nxCredit;
    }

    public void setNxCredit(int nxCredit) {
        this.nxCredit = nxCredit;
    }

    public void addLinkSkill(LinkSkill linkSkill) {
        removeLinkSkillByOwnerID(linkSkill.getOwnerID());
        getLinkSkills().add(linkSkill);
    }

    public void addLinkSkill(Char originChar, int sonID, int linkedSkill) {
        int level = SkillConstants.getLinkSkillLevelByCharLevel(originChar.getLevel());
        LinkSkill ls = new LinkSkill(originChar.getId(), linkedSkill, level);
        addLinkSkill(ls);
    }

    public void removeLinkSkillByOwnerID(int ownerID) {
        getLinkSkills().stream().filter(l -> l.getOwnerID() == ownerID).findFirst()
                .ifPresent(ls -> getLinkSkills().remove(ls));
    }

    public Set<LinkSkill> getLinkSkills() {
        return linkSkills;
    }

    public void setLinkSkills(Set<LinkSkill> linkSkills) {
        this.linkSkills = linkSkills;
    }

    public void addNXCredit(int credit) {
        int newCredit = getNxCredit() + credit;
        if (newCredit >= 0) {
            setNxCredit(newCredit);
        }
    }

    public void deductNXCredit(int credit) {
        addNXCredit(-credit);
    }

    public MonsterCollection getMonsterCollection() {
        if (monsterCollection == null) {
            monsterCollection = new MonsterCollection();
        }
        return monsterCollection;
    }

    public void setMonsterCollection(MonsterCollection monsterCollection) {
        this.monsterCollection = monsterCollection;
    }

    public boolean hasCharacter(int charID) {
        // doing a .contains on getCharacters() does not work, even if the hashcode is just a hash of the id
        return getCharById(charID) != null;
    }

    public Char getCharById(int id) {
        return Util.findWithPred(getCharacters(), chr -> chr.getId() == id);
    }

    public Char getCharByName(String name) {
        return Util.findWithPred(getCharacters(), chr -> chr.getName().equals(name));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWorldId() {
        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public Char getCurrentChr() {
        return currentChr;
    }

    public void setCurrentChr(Char currentChr) {
        this.currentChr = currentChr;
    }

    public EmployeeTrunk getEmployeeTrunk() {
        if (employeeTrunk == null) {
            employeeTrunk = new EmployeeTrunk();
        }

        return employeeTrunk;
    }


}
