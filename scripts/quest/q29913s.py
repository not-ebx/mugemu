# Legendary Thief

medal = 1142012
echo = 1005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Legendary Thief> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
