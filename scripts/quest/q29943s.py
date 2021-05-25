# Special Training Graduate

medal = 1142244

if sm.canHold(medal):
    sm.chatScript("You obtained the <Special Training Graduate> medal.")
    sm.giveItem(medal)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
