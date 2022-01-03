# [Job Advancement] Type E: Data Upgrade (22800) | Kinesis 3rd Job

from net.swordie.ms.enums import InvType

rookMedal = 1142865
rookChess = 1353202

jay = 1531007

sm.setSpeakerID(jay)
sm.flipDialogue()
sm.sendNext("What's up, K? Enjoying your little jaunt?")

sm.flipDialogue()
sm.sendSay("Hey, send me a pic of a monster or something. "
"My fifth monitor needs a new background.")

sm.flipDialogue()
sm.sendSay("Did you wreck them already? Geez, man...")

sm.flipDialogue()
response = sm.sendAskYesNo(''.join(["Whatever. I think we can agree that you're ready for the next upgrade. "
"Any objections? #b\r\n\r\n"
"(Accept for 3rd Job Advancement.) \r\n\r\n"
"#i", repr(rookMedal), "# #z", repr(rookMedal), "# \r\n"
"#i", repr(rookChess), "# #z", repr(rookChess), "#"]))
if response:
    if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
        sm.jobAdvance(14211)
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.giveItem(rookMedal)
        sm.giveItem(rookChess)

        sm.flipDialogue()
        sm.sendNext("There you go. If I did the math right, this should let you levitate monsters yourself.")

        sm.flipDialogue()
        sm.sendPrev("A whole new world of owning just opened up for you. "
        "I almost feel sorry for those monsters. Especially the cute ones.")
    else:
        sm.flipDialogue()
        sm.sendSayOkay("Hey, make some space in your Equip inventory first.")
else:
    sm.flipDialogue()
    sm.sendSayOkay("Fine, I'll be here all day. My guild is raiding tonight.")