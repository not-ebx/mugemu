# Special Training Master

medal = 1142246
echo = 30001005

if sm.canHold(medal) and sm.hasQuestCompleted(23060):
    sm.chatScript("You have earned a new medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
