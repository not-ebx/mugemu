# [Job Advancement] Pink Power Pact (25827) | AB 4th job advancement

from net.swordie.ms.enums import InvType

idol = 1142498
greenRing = 1352604

ESKALADE_NPC_ID = 3000018

if chr.getLevel() >= 100 and chr.getJob() == 6511:
    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendNext("You've been working up quite a sweat, #h #.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("I want to be the hero of the people! I gotta work my butt off! Angelic Savior!")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("I like it when you talk like that. I have a little proposition for you...")

    sm.setPlayerAsSpeaker()
    sm.sendSay("I hate it when you use big words like that. You sound so serious.")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("I'm always serious.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("That's... kinda scary.")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("...We need a new contract.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("Aww, another one?!")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("Our first contract was guided by fate! This one has to come from the heart! It's all about consensual bonding.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("That sounds weird. Is it gonna make everything more pink again?")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    response = sm.sendAskYesNo("Probably, but you can deal! Now focus on my exceedingly attractive voice, okay? "
    "Just really listen to the timbre.")
    if response:
        if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
            sm.setPlayerAsSpeaker()
            sm.jobAdvance(6512)
            sm.completeQuest(parentID)
            sm.giveItem(idol)
            sm.giveAndEquip(greenRing)
            sm.sendSayOkay("I'm tougher than all the rest!")
        else:
            sm.sendSayOkay("Please make room in your Equip inventory.")
