# Aurora Prism (2430874)

maps = [101000200, 101000101]

vieren = 1032209

currentMap = sm.getFieldID()
if currentMap in maps:
    maps.remove(currentMap)

sm.setSpeakerID(vieren)
destString = "Where would you like to go? #b\r\n"
for index, option in enumerate(maps):
    destString += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
destination = sm.sendNext(destString)

sm.setReturnField()
sm.setReturnPortal(0)
sm.warp(maps[destination])