# Last One Home

medal = 1142579

if sm.canHold(medal):
    sm.chatScript("You obtained the <Last One Home> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)