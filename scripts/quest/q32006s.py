# Secret Agent

medal = 1142576

if sm.canHold(medal):
    sm.chatScript("You obtained the <Secret Agent> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)