# Road to Oblivion 2 (270030200) => Road to Oblivion 3

# The One Who Walks Through the Road to Oblivion 2
if sm.hasQuestCompleted(3516):
    sm.warp(270030300, 4)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270030100)