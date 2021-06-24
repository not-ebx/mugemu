# Fresh Start (57402)

from net.swordie.ms.enums import InvType

rewards = [
    (2000000, 100), # Red Potions
    (1000065, 1), # Lunar Onmyouji Hat
    (1003570, 1), # Lunar Hair Band
    (1052479, 1), # Lunar Short Onmyouji Robes
    (1082450, 1), # Lunar Bracelet
    (1072684, 1), # Lunar Shoes
    (1552000, 1), # Iron Fan
    (1142506, 1), # Yin and Yang
]

gender = chr.getAvatarData().getAvatarLook().getGender()

if gender == 1:
    rewards.remove((1000065, 1))
else:
    rewards.remove((1003570, 1))

kanetsugu = 9130082

sm.removeEscapeButton()
sm.setSpeakerID(kanetsugu)
sm.setBoxChat()
if sm.getEmptyInventorySlots(InvType.EQUIP) >= 5 and sm.canHold(2000000):
    sm.sendNext("This is the new base, Momijigaoka. Mouri, a prominent member of the alliance, "
    "gathered the first to arrive in this new world and established a group.")

    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("Momijigaoka... Autumn's blood-red leaves. A fitting name.")

    sm.setSpeakerID(kanetsugu)
    sm.setBoxChat()
    sm.sendNext("Mouri wouldn't have it any other way. Go see him inside the stronghold. "
    "He has plans for you.")

    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("What about you?")

    sm.setSpeakerID(kanetsugu)
    sm.setBoxChat()
    sm.sendNext("I must continue searching for Kenshin-Daimyo. "
    "I can't shake the feeling that she's lost somewhere in this place, surrounded by danger, calling out for me. "
    "The thought is driving me crazy.")

    sm.completeQuest(parentID)
    for item in rewards:
        sm.giveItem(item[0], item[1])
else:
    sm.sendNext("Please make room in your Equip and Use inventories.")