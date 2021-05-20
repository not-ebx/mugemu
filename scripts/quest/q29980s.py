# True Knight of Light

medal = 1142403
echo = 50001005

if sm.canHold(medal):
    sm.chatScript("You have earned a new medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)