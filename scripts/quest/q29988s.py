# Kaiser's Fated Path

medal = 1142485

if sm.canHold(medal):
    sm.chatScript("You obtained the <Kaiser's Fated Path> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)