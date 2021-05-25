# Special Training Beginner

medal = 1142242

if sm.canHold(medal):
    sm.chatScript("You obtained the <Special Training Beginner> medal.")
    sm.giveItem(medal)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
