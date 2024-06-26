package net.mugeemu.ms.enums;


public enum TextEffectType {

    BlackFadedBrush(1),
    BurningField(2),
    TextNoBackground(3),
    ;

    private byte val;

    TextEffectType(int val) { this.val = (byte) val;}

    public byte getVal() { return val;}
}
