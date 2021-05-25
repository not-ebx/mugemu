# Special Training Intermediate

medal = 1142243

if sm.canHold(medal):
    sm.chatScript("You obtained the <Special Training Intermediate> medal.")
    sm.giveItem(medal)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
