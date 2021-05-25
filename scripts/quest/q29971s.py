# Master Thief Phantom

medal = 1142379
echo = 20031005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Master Thief Phantom> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)