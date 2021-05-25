# Master Adventurer

medal = 1142110

if sm.canHold(medal):
    sm.chatScript("You obtained the <Master Adventurer> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)