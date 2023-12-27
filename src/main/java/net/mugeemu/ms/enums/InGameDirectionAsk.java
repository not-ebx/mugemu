package net.mugeemu.ms.enums;

public enum InGameDirectionAsk {
    NOT(0),
    DELAY(1),
    PATTERN_INPUT_REQUEST(2),
    CAMERA_MOVE_TIME(3);

    private int val;

    InGameDirectionAsk(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public static InGameDirectionAsk getByVal(int val) {
        if (val >= 0 && val < values().length) {
            return values()[val];
        }
        return null;
    }
}
