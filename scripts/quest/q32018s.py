# Shadow Knight

medal = 1142634

if sm.canHold(medal):
    sm.chatScript("You obtained the <Shadow Knight> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)