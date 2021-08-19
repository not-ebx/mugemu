# Road of Regrets 4 (270020400) => Road of Regrets 5

# The One Who Walks Down the Road of Regrets4
if sm.hasQuestCompleted(3511):
    sm.warp(270020500, 3)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270020300)