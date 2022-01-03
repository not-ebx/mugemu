# [Skill] Cygnus Constellation (20899)

echo = 10001005
cygnusConstellation = 1142597

cygnus = 1101000

if sm.canHold(cygnusConstellation):
    sm.setSpeakerID(cygnus)
    sm.sendNext(''.join(["You have exceeded all our expectations. Please take this as a symbol of your heroism.\r\n"
    "#s", repr(echo), "# #q", repr(echo), "#\r\n"
    "#i", repr(cygnusConstellation), "# #z", repr(cygnusConstellation), "#"]))
    sm.completeQuest(parentID)
    sm.giveSkill(echo)
    sm.giveItem(cygnusConstellation)
else:
    sm.setSpeakerID(cygnus)
    sm.sendSayOkay("Please make room in your Equip inventory.")