# Opponent of Destiny

medal = 1142483
echo = 20041005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Opponent of Destiny> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)