# [Job Advancement] Agent of Justice (25825) | AB 2nd job advancement

from net.swordie.ms.enums import InvType

agent = 1142496
purpleRing = 1352602

ESKALADE_NPC_ID = 3000018

if chr.getLevel() >= 30 and chr.getJob() == 6500:
    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendNext("Looking pretty tough there, #h #.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("Yeah? I'm totally rocking this Heroine of Justice thing.")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("Have you noticed feeling a lot closer to me lately? Our pact has been getting stronger and stronger...")

    sm.setPlayerAsSpeaker()
    sm.sendSay("Uh, is that good?")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("What could be bad about getting closer to your dragon-lord? We need to move and act as one.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("That sounds complicated... and gross. What are you getting at?")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("I'm trying to let you use my muscle more! You never trust me...")

    sm.setPlayerAsSpeaker()
    sm.sendSay("I could use some more strength!")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    response = sm.sendAskYesNo("I knew you'd be swayed by a little extra power. Let's strengthen our bond.")
    if response:
        if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
            sm.jobAdvance(6510)
            sm.completeQuest(parentID)
            sm.giveItem(agent)
            sm.giveAndEquip(purpleRing)
            sm.sendNext("Now focus!")
        else:
            sm.sendSayOkay("Please make room in your Equip inventory.")
    else:
        sm.sendSayOkay("What happened to wanting more power?")