# Interdimensional Portal (3000012) | Great Temple Interior (400000001)

if sm.hasQuest(38030):
    sm.setQRValue(38030, "clear", False)
    sm.warp(100000000, 23)

else:
    fenelle = 3000001

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
        241000000, # Kritias
        ]

    sm.setSpeakerID(fenelle)
    destString = "You can use the Interdimensional Portal to move to any town. Where would you like to go? #b\r\n"
    for index, option in enumerate(maps):
        destString += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
    destination = sm.sendNext(destString)
    sm.warp(maps[destination])