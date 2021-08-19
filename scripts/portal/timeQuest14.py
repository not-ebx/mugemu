# Road to Oblivion 4 (270030400) => Resting Spot of Oblivion

# The One Who Walks Through the Road to Oblivion 4
if sm.hasQuestCompleted(3518):
    sm.warp(270030410, 3)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270030300)