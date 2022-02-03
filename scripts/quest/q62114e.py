# Welcome to Shanghai! (62114)

zhenLong = 9310532

yuGardenVisitor = 3700310

sm.setSpeakerID(zhenLong)
sm.setBoxChat()
sm.sendNext(''.join(["Here's your #i", repr(yuGardenVisitor), "# #z", repr(yuGardenVisitor), "# to prove you passed the test."]))
if sm.canHold(yuGardenVisitor):
    sm.giveExp(170598)
    sm.giveItem(yuGardenVisitor)
    sm.completeQuest(parentID)

    sm.sendNext("Now I can officially welcome you to Shanghai. We just have that teeny-weeny jiangshi problem right now. "
    "Sorry about that. But I hope you enjoy your stay!")
    sm.sendNext("I'm so sorry, I think I see another traveler. I hope you have a fun, happy, and SAFE stay at Yu Garden!")
else:
    sm.sendNext("I'm sorry, but can you make some room in your Set-up inventory first?")