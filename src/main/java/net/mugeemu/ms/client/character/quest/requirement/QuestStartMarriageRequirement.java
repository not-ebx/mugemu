package net.mugeemu.ms.client.character.quest.requirement;

import net.mugeemu.ms.loaders.DatSerializable;
import net.mugeemu.ms.client.character.Char;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created on 3/2/2018.
 */
public class QuestStartMarriageRequirement implements QuestStartRequirement {
    @Override
    public boolean hasRequirements(Char chr) {
        return chr.isMarried();
    }

    @Override
    public void write(DataOutputStream dos) {

    }

    @Override
    public DatSerializable load(DataInputStream dis) {
        return new QuestStartMarriageRequirement();
    }
}
