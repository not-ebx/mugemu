# King: Master of Space and Time (22870)

echo = 140001005
king = 1142867

threeMoon = 1531004

sm.setSpeakerID(threeMoon)
sm.flipDialogue()
sm.sendNext("You've gotten very strong, Kinesis. \r\n"
"You're not the neophyte I saw when you first arrived at Ellinia.")

sm.flipDialogue()
sm.sendSay("Your ability to manipulate your surroundings is peerless.")

sm.flipDialogue()
sm.sendSay("You have the power to change the world.")

sm.flipDialogue()
sm.sendSay("Control it, and don't let it go to your head.")

sm.flipDialogue()
sm.sendSay("This power is imbued with the wills of Maple World's guardians. "
"I'll share some of this power with you. I know you'll put it to good use.")

sm.flipDialogue()
response = sm.sendAskAccept(''.join(["And I'll also give you a token symbolizing your strength. #b\r\n\r\n"
"#i", repr(king), "# #z", repr(king), "#"]))
if response:
    if sm.canHold(king):
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
        sm.giveSkill(echo)
        sm.giveItem(king)
        sm.flipDialogue()
        sm.sendSayOkay("I admire your strength. Keep up the good work.")
    else:
        sm.flipDialogue()
        sm.sendSayOkay("Please make room in your Equip inventory.")
else:
    sm.flipDialogue()
    sm.sendSayOkay("Come back when you're ready to accept new power.")