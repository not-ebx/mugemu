# Battleground Idol

medal = 1142498

if sm.canHold(medal):
    sm.chatScript("You obtained the <Battleground Idol> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)