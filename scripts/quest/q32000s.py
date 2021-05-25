# Rage Acolyte

medal = 1142553

if sm.canHold(medal):
    sm.chatScript("You obtained the <Rage Acolyte> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)