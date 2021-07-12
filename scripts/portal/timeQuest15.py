# Road to Oblivion 5 (270030500) => Broken Corridor

# The One Who Walks Through the Road to Oblivion 5
if sm.hasQuestCompleted(3519):
    sm.warp(270040000, 3)
else:
    sm.chat("Those who do not have the Goddess' permission may not move against the flow of time, and will be sent back to their previous location.")
    sm.warp(270030410)