# Memory Lane 1 (270010100) => Resting Spot of Memory

# The One Who Walks Down Memory Lane 1
if sm.hasQuestCompleted(3501):
    sm.warp(270010110, 3)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270010000)