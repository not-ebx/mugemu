# Wiseman

medal = 1142010
echo = 1005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Wiseman> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
