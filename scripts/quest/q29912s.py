# Lord Sniper

medal = 1142011
echo = 1005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Lord Sniper> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
