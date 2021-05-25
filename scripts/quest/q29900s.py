# Beginner Adventurer

medal = 1142107

if sm.canHold(medal):
    sm.chatScript("You obtained the <Beginner Adventurer> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)