skyJewel = 3935
hallway = 260000302
treasureRoom = 926000010

if sm.hasQuest(skyJewel):
    sm.chat("The wall collapses, and in comes a secret door.")
    sm.warpInstanceIn(treasureRoom, False)