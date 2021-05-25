# Official Knight of Light

medal = 1142401

if sm.canHold(medal):
    sm.chatScript("You obtained the <Official Knight of Light> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)