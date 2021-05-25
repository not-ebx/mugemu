# Vengeance Incarnate

medal = 1142345

if sm.canHold(medal):
    sm.chatScript("You obtained the <Vengeance Incarnate> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)