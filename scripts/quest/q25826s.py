# [Job Advancement] Pink Power Rising (25826) | AB 3rd Job
from net.swordie.ms.enums import InvType

angel = 1142497
blueRing = 1352603

ESKALADE_NPC_ID = 3000018

if chr.getLevel() >= 60 and chr.getJob() == 6510:
    sm.setPlayerAsSpeaker()
    sm.sendNext("Eskalade, how come all my skills are getting... pinker?")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("Well, it IS my favorite color. Maybe it means you're getting better at using my power.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("Wait, your favorite color is pink? Why do I get the only dragon who loves cutesy things?")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("This whole thing would be a lot easier if you just gave in to my supreme will and played along.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("You seriously can't make the pink go away?")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("Nope! You can just deal with it. Besides, it's a good color for you. Brings out your rosy cheeks. "
                "Now, do you want to synchronize souls again?")

    sm.setPlayerAsSpeaker()
    sm.sendSay("Yeah, I guess so... I'll get stronger, right?")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    sm.sendSay("Absolutely! You will become my genuine pink angel.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("I really don't know about this...")

    sm.setSpeakerID(ESKALADE_NPC_ID)
    response = sm.sendAskYesNo("You have to make sacrifices to be a hero! Don't you want that?")
    if response:
        if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
            sm.setPlayerAsSpeaker()
            sm.jobAdvance(6511)
            sm.startQuest(parentID)
            sm.completeQuest(parentID)
            sm.giveItem(angel)
            sm.giveAndEquip(blueRing)
            sm.sendNext("I think I just got stronger!")
        else:
            sm.sendSayOkay("Please make room in your Equip inventory.")