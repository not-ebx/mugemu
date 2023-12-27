package net.mugeemu.ms.client.character.items;

import net.mugeemu.ms.loaders.EtcData;
import net.mugeemu.ms.enums.ScrollStat;
import net.mugeemu.ms.util.container.Tuple;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetEffect {
    private static final Logger log = Logger.getLogger(EtcData.class);
    private HashMap<Integer, List<Object>> effectsByLevel = new HashMap<>();

    public void addScrollStat(int level, ScrollStat ss, int amount) {
        List<Object> stats = effectsByLevel.getOrDefault(level, new ArrayList<>());
        stats.add(new Tuple<>(ss, amount));
        effectsByLevel.put(level, stats);
    }

    public void addOption(int level, ItemOption io) {
        List<Object> stats = effectsByLevel.getOrDefault(level, new ArrayList<>());
        stats.add(io);
        effectsByLevel.put(level, stats);
    }

    public HashMap<Integer, List<Object>> getEffectsToLevel() {
        return effectsByLevel;
    }

    public List<Object> getStatsByLevel (int level) {
        return effectsByLevel.get(level);
    }
}
