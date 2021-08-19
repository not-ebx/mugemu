# Road to Oblivion 3 (270030300) => Road to Oblivion 4

# The One Who Walks Through the Road to Oblivion 3
if sm.hasQuestCompleted(3517):
    sm.warp(270030400, 5)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270030200)