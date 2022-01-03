# Regular Cab in Victoria (1012000)

maps = [104000000, 100000000, 103000000, 101000000, 102000000, 120000100, 105000000]
currentMap = sm.getFieldID()
if currentMap in maps:
    maps.remove(currentMap)

destString = ["Where would you like to go?\r\n"]
for index, option in enumerate(maps):
    destString.append(''.join(["#L", repr(index), "##m", repr(option), "##l\r\n"]))
destination = sm.sendNext(''.join(destString))
sm.warp(maps[destination])