package net.mugeemu.ms.client.character.quest.reward;

import net.mugeemu.ms.loaders.DatSerializable;
import net.mugeemu.ms.client.character.Char;

/**
 * Created on 3/2/2018.
 */
public interface QuestReward extends DatSerializable {

    /**
     * Gives the reward of this QuestReward to a {@link Char}
     * @param chr The Char to give the reward to.
     */
    void giveReward(Char chr);
}
