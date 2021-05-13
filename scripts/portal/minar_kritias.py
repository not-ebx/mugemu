# Sky Nest I => Freezing Forest 2

toKritias = 241000218
currentLevel = chr.getLevel()

if currentLevel >= 170:
    sm.warp(toKritias)
else:
    sm.chat("You must be Level 170 or higher to enter Kritias.")
