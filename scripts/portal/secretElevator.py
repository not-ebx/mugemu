# Edelstein Strolling Path 3 (310030200) => Android Research Lab 3

verne = 310060120
currentLevel = chr.getLevel()

if currentLevel >= 60:
    sm.warp(verne)
else:
    sm.chat("You must be Level 60 or higher to use the elevator.")