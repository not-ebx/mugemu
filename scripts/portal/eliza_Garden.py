# Stairway to the Sky II (200010300) => Eliza's Garden

calmEliza = 3112
garden = 920020000

if sm.hasQuest(calmEliza) or sm.hasQuestCompleted(calmEliza):
    sm.warp(garden)
else:
    sm.chat("Eliza's rage is still permeating the garden. Calm him down first.")