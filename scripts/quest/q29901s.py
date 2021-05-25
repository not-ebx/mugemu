# Junior Adventurer

medal = 1142108

if sm.canHold(medal):
    sm.chatScript("You obtained the <Junior Adventurer> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)