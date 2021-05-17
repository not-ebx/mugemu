# [Future] Dreamy Forest Trail (271010400) => Six Path Crossway Ruins

toSixPath = 271010500
currentLevel = chr.getLevel()

if currentLevel >= 180:
    sm.warp(toSixPath)
else:
    sm.chat("You must be Level 180 or higher to enter the Six Path Crossway Ruins.")
