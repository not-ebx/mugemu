package net.swordie.ms.client.character.items;

import net.swordie.ms.enums.BaseStat;
import net.swordie.ms.enums.ItemGrade;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 1/26/2018.
 */
public class ItemOption {
    private int optionType;
    private int weight;
    private int id;
    private int reqLevel;
    private Map<Integer, Map<BaseStat, Double>> statValuesPerLevel = new HashMap<>();
    private String string;
    private Map<Integer, Map<ItemOptionType, Integer>> miscValuesPerLevel = new HashMap<>();

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getOptionType() {
        return optionType;
    }

    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: " + getId() + ", optionType: " + getOptionType() + ", weight: " + getWeight();
    }

    public boolean hasMatchingGrade(short itemState) {
        return ItemGrade.isMatching(itemState, ItemGrade.getGradeByOption(getId()).getVal());
    }

    public boolean isBonus() {
        return getId() > 2000 && getId() / 1000 % 10 == 2;
    }

    public void setReqLevel(int reqLevel) {
        this.reqLevel = reqLevel;
    }

    public int getReqLevel() {
        return reqLevel;
    }

    public Map<Integer, Map<BaseStat, Double>> getStatValuesPerLevel() {
        return statValuesPerLevel;
    }

    public void addStatValue(int level, BaseStat baseStat, double value) {
        Map<BaseStat, Double> valMap = getStatValuesPerLevel().getOrDefault(level, new HashMap<>());
        valMap.put(baseStat, value);
        getStatValuesPerLevel().put(level, valMap);
    }

    public Map<Integer, Map<ItemOptionType, Integer>> getMiscValuesPerLevel() {
        return miscValuesPerLevel;
    }

    public void addMiscValue(int level, ItemOptionType type, int value) {
        Map<ItemOptionType, Integer> valMap = getMiscValuesPerLevel().getOrDefault(level, new HashMap<>());
        valMap.put(type, value);
        getMiscValuesPerLevel().put(level, valMap);
    }

    public Map<BaseStat, Double> getStatValuesByLevel(int level) {
        return getStatValuesPerLevel().getOrDefault(level, new HashMap<>());
    }

    public Map<ItemOptionType, Integer> getMiscValuesByLevel(int level) {
        return getMiscValuesPerLevel().getOrDefault(level, new HashMap<>());
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString(int level) {
        level = level / 10;
        String str = getString();
        int val = -1;
        String[] split = str.split("#");

        if (getMiscValuesByLevel(level).isEmpty()) {
            String[] split2 = split[1].split("[% ]");
            String opt = split2[0];
            for (Map.Entry<BaseStat, Double> e : getStatValuesByLevel(level).entrySet()) {
                val = e.getValue().intValue();
                str = str.replace("#" + opt, val + "");
            }
        } else {
            for (String currentString : split) {
                String opt = removeSpecialCharacters(currentString.split(" ")[0]);
                if (!currentString.equals("") && doesItemOptionTypeExists(opt)) {
                    val = getMiscValuesByLevel(level).get(ItemOptionType.valueOf(opt));
                    str = str.replace("#" + opt, val + "");
                }
            }
        }
        return str;
    }

    public boolean doesItemOptionTypeExists(String typeForCheck) {
        for (ItemOptionType types : ItemOptionType.values()) {
            if (types.toString().equals(typeForCheck)) {
                return true;
            }
        }
        return false;
    }

    public boolean doesBaseStatTypeExists(String typeForCheck) {
        for (BaseStat types : BaseStat.values()) {
            if (types.toString().equals(typeForCheck)) {
                return true;
            }
        }
        return false;
    }

    public String removeSpecialCharacters(String str) {
        return str.replaceAll("[!\"#$%&’()*+,-./:;<=>?@\\[\\]^_`{|}~]","");
    }

    public String getString() {
        return string;
    }

    public enum ItemOptionType {
        prop,
        face,
        attackType,
        level,
        ignoreDAMr,
        ignoreDAM,
        DAMreflect,
        HP,
        MP,
        time
    }
}
