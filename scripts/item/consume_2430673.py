# Burning Sunburst (2430673)

sunburst = 4001684

if not sm.hasItem(parentID, 10):
    sm.chat("10 Burning Sunbursts are required to create a concentrated Sunburst.")
elif not sm.canHold(sunburst):
    sm.chat("Please make room in your Etc. inventory.")
else:
    sm.consumeItem(parentID, 10)
    sm.giveItem(sunburst)