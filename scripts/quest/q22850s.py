# [Job Advancement] Type S: Data Upgrade (22850) | Kinesis 4th Job

from net.swordie.ms.enums import InvType

# This medal should really be awarded by 22861, but since Kinesis' quests are unscripted...
queenMedal = 1142866
queenChess = 1353203

jay = 1531007

sm.setSpeakerID(jay)
sm.flipDialogue()
sm.sendNext("Holy-! K, your psy-limiter is maxed out! "
"Where did all that power come from?")

sm.flipDialogue()
sm.sendSay("Man, you really took it to the limit. "
"I guess this means you can finally handle yourself in the sinkhole, huh.")

sm.flipDialogue()
response = sm.sendAskYesNo("This might be a good time to upgrade again. You ready? #b\r\n\r\n"
"(Accept for 4th Job Advancement.) \r\n\r\n"
"#i" + str(queenMedal) + "# #z" + str(queenMedal) + "# \r\n"
"#i" + str(queenChess) + "# #z" + str(queenChess) + "#")
if response:
    if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
        sm.jobAdvance(14212)
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.giveItem(queenMedal)
        sm.giveItem(queenChess)
        sm.flipDialogue()
        sm.sendSayOkay("Welp, I think you've achieved your final form. "
        "I would check your power level, but you might explode my stuff.")
    else:
        sm.flipDialogue()
        sm.sendSayOkay("Hey, make some space in your Equip inventory first.")
else:
    sm.flipDialogue()
    sm.sendSayOkay("Fine, I'll be here all day. My guild is raiding tonight.")