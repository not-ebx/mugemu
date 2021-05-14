# Forced warp to Castle Garden during 3rd Red Scorpions quest

queensRing = 3923
garden = 260000301

if sm.hasQuest(queensRing):
    #Uh oh, you were spotted
    sm.chat("Intruder alert! You need to be more careful sneaking inside the palace.")
    sm.warp(garden)