# Leafre Treasure Vault Entrance (915020200) => Leafre Treasure Vault

if not sm.hasMobsInField():
    sm.warp(915010201, 2)
elif sm.hasMobsInField():
    sm.chat("Disable the haywire Guardioso first!")