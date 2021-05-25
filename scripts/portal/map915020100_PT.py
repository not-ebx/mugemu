# Ariant Treasure Vault Entrance (915020100) => Ariant Treasure Vault

if 2400 <= chr.getJob() <= 2412 and not sm.hasMobsInField():
    sm.warp(915020101, 1)
elif sm.hasMobsInField():
    sm.chat("Eliminate all of the intruders first.")