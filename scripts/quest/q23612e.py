# [Job Advancement] Only the Brave (23612) | Xenon 3rd Job

captainFreedom = 1142577

promathus = 2300002
gelimer = 2154009

sm.setSpeakerID(promathus)
sm.sendNext("That took longer than I expected. Is everything all right?")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("(You explain that you were almost discovered by the Black Wings.)")

sm.setSpeakerID(promathus)
sm.sendSay("Haha! #p" + str(gelimer) + "# would be furious if he knew you'd gotten by right under his nose. "
"Quite a daring move, yes, yes.")
response = sm.sendAskYesNo("Well done, child, well done. Your courage is evident. "
"Will you take what we have readied for you?")
if response:
    if sm.canHold(captainFreedom):
        sm.jobAdvance(3611)
        sm.completeQuest(parentID)
        sm.giveItem(captainFreedom)
        sm.sendSayOkay("I've uploaded every bit of information on the Black Wings and their agents to your memory banks. "
        "Your neural interface should create a sort of camouflage effect, should any Black Wings cross your path. "
        "To them, you will appear as someone they do not know. It should even work on #p" + str(gelimer) + "#.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")