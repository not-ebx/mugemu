# Black Portal (2153004) | Hidden Portal (310040110)

import random

harryLion = 23944
bwToken = 4032766

victoriaIsland = [100000000, 101000000, 102000000, 103000000, 104000000, 120000000]

def warpToVictoriaIsland():
    destination = random.choice(victoriaIsland)
    sm.warp(destination)

if sm.hasQuestCompleted(harryLion):
    if sm.hasItem(bwToken):
        sm.consumeItem(bwToken)
        warpToVictoriaIsland()
    elif sm.getMesos() >= 10000:
        sm.chat("Consumed 10000 mesos instead of a Black Wings Token to operate the Black Portal.")
        sm.deductMesos(10000)
        warpToVictoriaIsland()
    else:
        sm.chat("You need 10000 mesos to operate the Black Portal without a Black Wings Token.")
else:
    sm.chat("You don't have permission to use the Black Portal.")