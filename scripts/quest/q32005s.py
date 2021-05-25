# Memory Seeker

medal = 1142575

if sm.canHold(medal):
    sm.chatScript("You obtained the <Memory Seeker> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)