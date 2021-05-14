# Ice Valley II (211040200) => Sharp Cliff I

sharpCliff = 211040300
currentLevel = chr.getLevel()

if currentLevel >= 60:
    sm.warp(sharpCliff, 5)
else:
    sm.chat("You must be Level 60 or higher to enter the Dead Mine.")