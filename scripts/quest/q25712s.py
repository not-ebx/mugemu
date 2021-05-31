# [Job Advancement] Call of the Kaiser (25712) | Kaiser 4th job advancement

from net.swordie.ms.enums import InvType

successor = 1142487
truthEssence = 1352503

former = 3000011
pantheon = 400000000
beldar = 3000002

sm.setSpeakerID(former)
if chr.getJob() == 6111:
    sm.sendNext("Are you #h #?")

    sm.setPlayerAsSpeaker()
    sm.sendSay("W-Whoa! Who was that? Is someone spying on me?")

    sm.setSpeakerID(former)
    sm.sendSay("Don't be alarmed. I am the previous Kaiser, the one who came before you.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("P-previous Kaiser?")

    sm.setSpeakerID(former)
    response = sm.sendAskYesNo("Are you ready to listen to me?")
    if response:
        if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
            sm.jobAdvance(6112)
            sm.completeQuest(parentID)
            sm.giveItem(successor)
            sm.giveAndEquip(truthEssence)

            sm.sendNext("A few decades ago, I went to Heliseum to free it from Darmoor's clutches, "
            "but Magnus caught me in a surprise in a surprise attack. "
            "I had to stop him and his Specters from reaching #m" + str(pantheon) + "m#, "
            "and buy some time for the survivors to get to #m" + str(pantheon) + "m# safely.")
            sm.sendSay("If I didn't stop them there, they could have conquered Pantheon as well. "
            "Our shield at #m" + str(pantheon) + "m# was down, and I was the last line of defense. "
            "You might think #p" + str(beldar) + "# was cowardly to escape with the Nova Relics "
            "to #m" + str(pantheon) + "m#, but it was the right call.")
            sm.sendSay("Magnus poisoned me in our battle, and I was forced to sacrifice myself "
            "to unleash Ancestral Prominence, wiping out Magnus and his Specters...")

            sm.setPlayerAsSpeaker()
            sm.sendSay("But Magnus is still alive.")

            sm.setSpeakerID(former)
            sm.sendSay("Yes....Magnus should have been destroyed, but Darmoor's immense power "
            "spared him and restored his strength.")
            sm.sendSay("Magnus is being sustained by Darmoor's power. "
            "Without it, he would surely fall from the wounds I inflicted on him.")

            sm.setPlayerAsSpeaker()
            sm.sendSay("I heard somewhere that Magnus gained power from Maple World to restore his life...?")

            sm.setSpeakerID(former)
            sm.sendSay("Enough about the past. We need to talk about you, and your Essence.")

            sm.setPlayerAsSpeaker()
            sm.sendSay("What? Essence?")

            sm.setSpeakerID(former)
            sm.sendSay("Yes. Kaiser is a spirit that reincarnates. "
            "His Essence passes on from chosen to chosen, and it shall pass from me to you.")
            sm.sendPrev("This is the moment that you truly take on the power of Kaiser. "
            "I will grant you my Essence. Take it, and grow with its strength. "
            "I will return when you are prepared to take the title of Grand Kaiser.")
        else:
            sm.sendSayOkay("First, I need you to clear out two slots in your Equip tab.")
else:
    sm.sendSayOkay("You're currently not a third job Kaiser.")
