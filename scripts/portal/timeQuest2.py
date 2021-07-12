# Memory Lane 2 (270010200) => Memory Lane 3

# The One Who Walks Down Memory Lane 2
if sm.hasQuestCompleted(3502):
    sm.warp(270010300, 6)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270010110)