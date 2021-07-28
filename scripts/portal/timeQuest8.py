# Road of Regrets 3 (270020300) => Road of Regrets 4

# The One Who Walks Down the Road of Regrets3
if sm.hasQuestCompleted(3510):
    sm.warp(270020400, 6)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270020210)