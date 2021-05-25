# Ruler of Elves

medal = 1142340

if sm.canHold(medal):
    sm.chatScript("You obtained the <Ruler of Elves> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)