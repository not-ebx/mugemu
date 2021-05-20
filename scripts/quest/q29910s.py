# Gallant Warrior

medal = 1142009
echo = 1005

if sm.canHold(medal):
    sm.chatScript("You have earned a new medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
