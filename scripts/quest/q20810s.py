# Apprentice Knight (20810) | Mihile 2nd Job

from net.swordie.ms.enums import InvType

apprentice = 1142400
gladius = 1302038
prideShield = 1098001

cygnus = 2520025

sm.setSpeakerID(cygnus)
response = sm.sendAskYesNo("Congratulations on passing your trials, Mihile. "
"Are you prepared to be declared an official apprentice knight?")
if response:
    if sm.getEmptyInventorySlots(InvType.EQUIP) >= 3:
        if chr.getJob() == 5100:
            sm.jobAdvance(5110)
            sm.completeQuest(parentID)
            sm.giveItem(apprentice)
            sm.giveItem(gladius)
            sm.giveAndEquip(prideShield)
            sm.sendSayOkay("I now pronounce you an Apprentice Knight! "
            "I've given you some SP to use. Make us proud.")
        else:
            sm.sendSayOkay("You are not a 1st job Mihile.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")