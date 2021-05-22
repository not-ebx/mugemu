# Special Training Superior

medal = 1142245

if sm.canHold(medal):
    sm.chatScript("You obtained the <Special Training Superior> medal.")
    sm.giveItem(medal)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
