# Newborn Light

medal = 1142399

if sm.canHold(medal):
    sm.chatScript("You obtained the <Newborn Light> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)