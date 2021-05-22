# Leafre Treasure Vault Entrance (915010200) => Leafre Treasure Vault

if 2400 <= chr.getJob() <= 2412:
    if sm.hasQuest(25122):
        sm.warp(915010201, 2)
    else:
        sm.warp(915020201, 2)
else:
    sm.chat("Only Phantoms may enter.")