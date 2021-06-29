# Path of a Mechanic (23013) | Mechanic 1st job advancement quest

from net.swordie.ms.enums import InvType

pistol = 1492000
magnum = 1352700

checky = 2151004

sm.setSpeakerID(checky)
if sm.sendAskYesNo("Would you like to become a Mechanic?"):
    if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
        sm.jobAdvance(3500)
        sm.resetAP(False, 3500)
        sm.completeQuest(parentID)
        sm.giveItem(pistol)
        sm.giveAndEquip(magnum)
        sm.sendSayOkay("Congratulations, you are now a Mechanic! I have given you some SP and items to start out with, enjoy!")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")
else:
    sm.sendSayOkay("Come back when you're ready.")