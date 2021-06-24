# [Job Advancement] Type N Data Upgrade (22770) | Kinesis 2nd Job

from net.swordie.ms.enums import InvType

knightMedal = 1142864
knightChess = 1353201

jay = 1531007

sm.setSpeakerID(jay)
sm.flipDialogue()
sm.sendNext("K? Kinesis? Hello-ooo? You still in there?")

sm.flipDialogue()
sm.sendSay("THERE you are. I figured you wouldn't bite it that easily. "
"Can't get a trace on you, though... where did you end up?")

sm.flipDialogue()
sm.sendSay("I'm guessing you have quite a story. Yuna's waiting for you too, so hustle.")

sm.flipDialogue()
sm.sendSay("I bet your power level rose since I saw you last. "
"I can't WAIT to chart you.")

sm.flipDialogue()
response = sm.sendAskYesNo("You wanna go ahead and update your data? Say yes. #b\r\n\r\n"
"(Accept for 2nd Job Advancement.) \r\n\r\n"
"#i" + str(knightMedal) + "# #z" + str(knightMedal) + "# \r\n"
"#i" + str(knightChess) + "# #z" + str(knightChess) + "#")
if response:
    if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
        sm.jobAdvance(14210)
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.giveItem(knightMedal)
        sm.giveItem(knightChess)

        sm.flipDialogue()
        sm.sendSayOkay("Boom. Upgrade complete. Why not try out the goods?")
    else:
        sm.flipDialogue()
        sm.sendSayOkay("Hey, make some space in your Equip inventory first.")
else:
    sm.flipDialogue()
    sm.sendSayOkay("Fine, I'll be here all day. My guild is raiding tonight.")