# Used to remove the Red Draco Morph Buff In Leafre : Station in case field/undomorphdarco.py fails
from net.swordie.ms.client.character.skills.temp import CharacterTemporaryStat

if sm.getnOptionByCTS(CharacterTemporaryStat.Morph) == 16:
    sm.removeCTS(CharacterTemporaryStat.Morph)