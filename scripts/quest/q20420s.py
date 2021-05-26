# [Skill] A Great Honor (20420)

echo = 50001005
trueKnight = 1142403

cygnus = 1101000

sm.setSpeakerID(cygnus)
response = sm.sendAskYesNo("Knight of light, protector of virtue, your presence has been an inspiration for our people. "
"I only hope I do not place too much weight on your shoulders, for I have another favor I must ask. \r\n"
"P-please. Take on the training of the Noblesse who wish to follow you as Dawn Warriors. You're the only one who can!")
if response:
    if sm.canHold(trueKnight):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.giveSkill(echo)
        sm.giveItem(trueKnight)
        sm.sendSayOkay("I have given you a medal befitting a teacher "
        "and the skill necessary to become a member of the Maple Alliance. "
        "I trust we'll see great things from you.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")