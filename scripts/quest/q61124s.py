# Title - The Bladed Falcon's Medal (61124)

medal = 1142491

if sm.canHold(medal):
    sm.chatScript("You obtained <The Bladed Falcon's Medal>.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)