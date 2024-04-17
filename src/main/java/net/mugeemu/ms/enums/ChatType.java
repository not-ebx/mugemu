package net.mugeemu.ms.enums;

/**
 * Created on 1/2/2018.
 */
public enum ChatType {
    // 0: Normal Chat
    // 1: Whisper
    // 2: Party
    // 3: Buddy
    // 4: Guild
    // 5: Alliance
    // 6: Spouse [Dark Red]
    // 7: Grey
    // 8: Yellow
    // 9: Light Yellow
    // 10: Blue
    // 11: White
    // 12: Red
    // 13: Light Blue

    Normal(0),
    Whisper(1),
    GroupParty(2),
    GroupFriend(3),
    GroupGuild(4),
    GroupAlliance(5),
    GameDesc(6),
    Tip(7),
    Notice(8),
    Notice2(9),
    AdminChat(10),
    SystemNotice(11),
    SpeakerChannel(12),
    SpeakerWorld(13),
    SpeakerWorldGuildSkill(14),
    Mob(13),
    Expedition(13),
    BlackOnWhite(13),
    ;
    private short val;

    ChatType(short val) {
        this.val = val;
    }

    ChatType(int i) {
        this((short) i);
    }

    public short getVal() {
        return val;
    }
}
