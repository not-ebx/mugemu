# Elven Hero

medal = 1142339

if sm.canHold(medal):
    sm.chatScript("You obtained the <Elven Hero> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)