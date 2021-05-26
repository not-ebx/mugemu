# Destroying the Energy Conducting Device (23035) | Mechanic 3rd job advancement quest

vitalMagnum = 1352702

checky = 2151004

sm.setSpeakerID(checky)
if sm.sendAskYesNo("Would you like to advance to the next level?"):
    if sm.canHold(vitalMagnum):
        sm.jobAdvance(3511)
        sm.completeQuest(parentID)
        sm.giveAndEquip(vitalMagnum)
        sm.sendSayOkay("Congratulations, you are now at the next level! I have given you some SP, enjoy!")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")
else:
    sm.sendSayOkay("Come back when you're ready.")