# Palace Hallway (260000302) => Treasure Room of Queen for Eleska's Test

skyJewel = 3935
treasureRoom = 926000010

if sm.hasQuest(skyJewel):
    sm.chat("The wall collapses, and in comes a secret door.")
    sm.warpInstanceIn(treasureRoom, False)