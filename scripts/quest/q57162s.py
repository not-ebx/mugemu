# Maple Adjustment Period (57162) | Hayato 2nd Job

bladedFalcon = 1142491

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("This battle is not over, yet I feel a sense of peace. This world is not so different from my own.")
sm.sendNext("I feel like my old self, yet stronger. I believe this world holds many new challenges for me.")
sm.sendNext("I have not yet regained all of my Battoujutsu skills, but one day they will return. "
"One day, the falcon will take flight once again.")
if sm.canHold(bladedFalcon):
    sm.jobAdvance(4110)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    sm.giveItem(bladedFalcon)
else:
    sm.sendNext("Please make space in your Equip inventory.")
