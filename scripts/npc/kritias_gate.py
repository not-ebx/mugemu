# Kerkaporta (2230212) | Ranheim Academy (241000110)

guard = 2232210

maps = [
    104020000, # Six Path Crossway
    100000000, # Henesys
    101000000, # Ellinia
    102000000, # Perion
    103000000, # Kerning City
    104000000, # Lith Harbor
    105000000, # Sleepywood
    120000000, # Nautilus Harbor
    130000000, # Ereve
    140000000, # Rien
    200000000, # Orbis
    211000000, # El Nath
    220000000, # Ludibrium
    230000000, # Aquarium
    240000000, # Leafre
    250000000, # Mu Lung
    251000000, # Herb Town
    260000000, # Ariant
    261000000, # Magatia
    310000000, # Edelstein
    101050000, # Elluel
    400000000 # Pantheon
    ]

sm.setSpeakerID(guard)
destString = ["This gate will take you out of the kingdom. Where would you like to go? #b\r\n"]
for index, option in enumerate(maps):
    destString.append(''.join(["#L", repr(index), "##m", repr(option), "##l\r\n"]))
destination = sm.sendNext(''.join(destString))
sm.warp(maps[destination])