# Pantheon Return Scroll (2432316)
sm.flipDialoguePlayerAsSpeaker()
response = sm.sendAskAccept("Would you like to go to Pantheon? #r\r\n\r\n"
"(You will be moved to Pantheon right away if you accept.)")
if response:
    sm.levelUntil(30)
    sm.warp(400000000)
    sm.completeQuest(38028)
    sm.consumeItem(parentID)