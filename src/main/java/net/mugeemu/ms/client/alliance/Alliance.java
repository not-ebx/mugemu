package net.mugeemu.ms.client.alliance;

import net.mugeemu.ms.connection.Encodable;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.client.guild.Guild;

import jakarta.persistence.*;
import java.util.*;

/**
 * @author Sjonnie
 * Created on 9/1/2018.
 */
@Entity
@Table(name = "alliances")
public class Alliance implements Encodable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_allianceid")
    private Set<Guild> guilds = new HashSet<>();
    @ElementCollection
    @CollectionTable(name = "alliance_gradenames", joinColumns = @JoinColumn(name = "allianceID"))
    @Column(name = "gradeName")
    private List<String> gradeNames;
    private int maxMemberNum;
    private String notice;

    public Alliance() {
        gradeNames = Arrays.asList("Master", "Junior", "Veteran", "Regular", "New");
    }

    @Override
    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(getId());
        outPacket.encodeString(getName());
        for (String gradeName : getGradeNames()) {
            outPacket.encodeString(gradeName);
        }
        outPacket.encodeByte(getGuilds().size());
        for (Guild guild : getGuilds()) {
            outPacket.encodeInt(guild.getId());
        }
        outPacket.encodeInt(getMaxMemberNum());
        outPacket.encodeString(getNotice());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Guild> getGuilds() {
        return guilds;
    }

    public void setGuilds(Set<Guild> guilds) {
        this.guilds = guilds;
    }

    public List<String> getGradeNames() {
        return gradeNames;
    }

    public void setGradeNames(List<String> gradeNames) {
        this.gradeNames = gradeNames;
    }

    public int getMaxMemberNum() {
        return maxMemberNum;
    }

    public void setMaxMemberNum(int maxMemberNum) {
        this.maxMemberNum = maxMemberNum;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Guild getGuildByID(int guildID) {
        return getGuilds().stream().filter(g -> g.getId() == guildID).findAny().orElse(null);
    }

    public void broadcast(OutPacket outPacket) {
        for (Guild guild : getGuilds()) {
            guild.broadcast(outPacket);
        }
    }

    public void broadcast(OutPacket outPacket, Char exceptChar) {
        for (Guild guild : getGuilds()) {
            guild.broadcast(outPacket, exceptChar);
        }
    }

    public void addGuild(Guild guild) {
        getGuilds().add(guild);
    }

    public void removeGuild(Guild guild) {
        Guild g = getGuildByID(guild.getId()); // to ensure it's the same instance as the one in the set
        g.setAllianceID(0);
        getGuilds().remove(g);
    }
}
