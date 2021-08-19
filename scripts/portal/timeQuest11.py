# Road to Oblivion 1 (270030100) => Road to Oblivion 2

# The One Who Walks Through the Road to Oblivion 1
if sm.hasQuestCompleted(3515):
    sm.warp(270030200, 5)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270030000)