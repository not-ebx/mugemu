# Veteran Adventurer

medal = 1142109

if sm.canHold(medal):
    sm.chatScript("You obtained the <Veteran Adventurer> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)