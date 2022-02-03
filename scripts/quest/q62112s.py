# Just a Little Test 1 (62112)

zhenLong = 9310532
huangZhen = 9310536

sm.setSpeakerID(zhenLong)
sm.setBoxChat()
sm.sendNext("Oh, you're a wonderful human being for not giving me any trouble! At least, I hope you are...")
response = sm.sendAskYesNo("The test is simple. Just go to #p" + repr(huangZhen) + "#, the chef in Yu Garden, "
"and get some red bean porridge and garlic.")
if response:
    sm.startQuest(parentID)
    
    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("That's... the test? Isn't that a little too easy?")

    sm.setSpeakerID(zhenLong)
    sm.setBoxChat()
    sm.sendNext("I know, it is. It IS too easy. I'm so sorry!")