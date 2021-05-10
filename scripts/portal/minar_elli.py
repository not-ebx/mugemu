elliniaSide = 101030100
minarSide = 240010100

magicSeed = 4031346

if sm.hasItem(magicSeed):
    sm.consumeItem(magicSeed)
    
    if sm.getFieldID() == minarSide:
        sm.warp(elliniaSide, 5)
    else:
        sm.warp(minarSide, 5)
else:
    sm.chat("You need a Magic Seed to use this portal.")