LOCK = 9001045
if not sm.hasQuest(25101) and not sm.hasQuestCompleted(25101):
    sm.completeQuest(25100)
    sm.startQuestNoCheck(25101)
    sm.spawnMob(LOCK, 170, 182, False)