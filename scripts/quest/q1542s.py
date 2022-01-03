# [Skill] Missing Each Other (1542)

spiritBondMax = 25121131
foxTree = 940200070

sm.flipDialoguePlayerAsSpeaker()
sm.sendNext("Even though you expected it, the fact that Mercedes doesn't remember you really hurts.")
sm.sendSay("The only reason you grew stronger was because of the guardian spirit that Moonbeam bestowed upon you.")
sm.sendSay("You initially intended to return the spirit after everything was handled.")
sm.sendSay("The next time you see Moonbeam, show her her own spirit. "
"That's a surefire way to prove you know her.")
sm.sendSay("But we can't put this off any longer. She must be lonely without her guardian spirit!")
sm.sendSay("I know you really hoped that Moonbeam would remember you when you showed her the spirit, "
"but it looks like you might want to let go of that hope.")
response = sm.sendAskAccept("You better go see Moonbeam before it gets any later. #e#b\r\n\r\n"
"(You will be instantly moved to Fox Point Village if you accept.)")

if response:
    if sm.hasSkill(spiritBondMax):
        sm.sendNext("Head to Fox Point Village. Moonbeam will probably be somewhere nearby.")
        sm.setReturnField()
        sm.setReturnPortal(0)
        sm.warpInstanceIn(foxTree)
    else:
        sm.sendSayOkay(''.join(["Please learn #s", repr(spiritBondMax), "# #b#q", repr(spiritBondMax), "##k first."]))