package net.mugeemu.ms.enums;

/**
 * Created on 6/8/2018.
 */
public enum EliteState {
    None(0),
    EliteMob(1),
    EliteBoss(2),
    BonusStage(3);

    private int val;

    EliteState(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
