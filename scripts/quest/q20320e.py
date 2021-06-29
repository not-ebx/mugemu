# Knight's Qualification Exam (20320) | Mihile 3rd Job

from net.swordie.ms.enums import InvType

official = 1142401
courageShield = 1098002

neinhart = 1101002

sm.setSpeakerID(neinhart)
response = sm.sendAskYesNo("Now you're a REAL knight. Would you like to take your Job Advancement?")
if response:
    if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
        if chr.getJob() == 5110:
            sm.jobAdvance(5111)
            sm.completeQuest(parentID)
            sm.giveItem(official)
            sm.giveAndEquip(courageShield)
        else:
            sm.sendSayOkay("You are not a 2nd job Mihile.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")
    