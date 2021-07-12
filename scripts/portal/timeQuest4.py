# Memory Lane 4 (270010400) => Memory Lane 5

# The One Who Walks Down Memory Lane 4
if sm.hasQuestCompleted(3504):
    sm.warp(270010500, 7)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270010300)