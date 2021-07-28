# Memory Lane 3 (270010300) => Memory Lane 4

# The One Who Walks Down Memory Lane 3
if sm.hasQuestCompleted(3503):
    sm.warp(270010400, 5)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270010200)