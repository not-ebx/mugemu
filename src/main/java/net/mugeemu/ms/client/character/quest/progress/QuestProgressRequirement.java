package net.mugeemu.ms.client.character.quest.progress;

import net.mugeemu.ms.loaders.DatSerializable;
import net.mugeemu.ms.client.character.Char;

import jakarta.persistence.*;

/**
 * Created on 3/2/2018.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "questprogressrequirements")
@DiscriminatorColumn(name = "progressType")
public abstract class QuestProgressRequirement implements DatSerializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // order of encoding for quest record messages
    @Column(name = "orderNum")
    private int order = 999;

    /**
     * Returns whether this progress requirement has been completed by the player.
     * @return Completeness.
     */
    public abstract boolean isComplete(Char chr);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public abstract QuestProgressRequirement deepCopy();

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
