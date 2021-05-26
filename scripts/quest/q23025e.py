# Revenge and Growth (23025) | Mechanic 2nd job advancement quest

splitMagnum = 1352701

checky = 2151004

sm.setSpeakerID(checky)
if sm.sendAskYesNo("Would you like to advance to the next level?"):
    if sm.canHold(splitMagnum):
        sm.jobAdvance(3510)
        sm.completeQuest(parentID)
        sm.giveAndEquip(splitMagnum)
        sm.sendSayOkay("Congratulations, you are now at the next level! I have given you some SP, enjoy!")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")
else:
    sm.sendSayOkay("Come back when you're ready.")