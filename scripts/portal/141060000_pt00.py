# 141060000
from net.mugeemu.ms.client.character.skills.temp import CharacterTemporaryStat

sm.warp(141000000, 1) # Middle of Strait : Glacial Observatory

# Ride Vehicle Check
if sm.getnOptionByCTS(CharacterTemporaryStat.RideVehicle) == 1930000: # Riena Skiff used in Riena Strait
    sm.removeCTS(CharacterTemporaryStat.RideVehicle)

