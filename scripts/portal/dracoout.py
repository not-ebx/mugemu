# Leafre Station (240000110) => Leafre Station Entrance
from net.swordie.ms.client.character.skills.temp import CharacterTemporaryStat

# Another Red Draco morph check to be absolutely sure
if sm.getnOptionByCTS(CharacterTemporaryStat.Morph) == 16:
    sm.removeCTS(CharacterTemporaryStat.Morph)
sm.warp(240000100)