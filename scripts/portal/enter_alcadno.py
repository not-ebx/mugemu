# Magatia (261000000) => Alcadno Society

forbiddenBook = 23270
chaseAndConspiracy = 23271

# Demon story quest junctions
if sm.hasQuest(forbiddenBook):
    sm.warpInstanceIn(926150000, False)
elif sm.hasQuestCompleted(forbiddenBook) and not sm.hasQuest(chaseAndConspiracy) and not sm.hasQuestCompleted(chaseAndConspiracy):
    sm.warpInstanceIn(926150010, False)
elif sm.hasQuest(chaseAndConspiracy):
    sm.warpInstanceIn(926150020, False)
else:
    sm.warp(261000020, 1)