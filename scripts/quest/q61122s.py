# Title - Medal of the Bushido (61122)

medal = 1142490

if sm.canHold(medal):
    sm.chatScript("You obtained the <Medal of the Bushido>.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)