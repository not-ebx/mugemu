# Forgotten Hero

medal = 1142672
echo = 20051005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Forgotten Hero> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)