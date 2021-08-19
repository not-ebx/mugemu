# Mysterious Portal (1033112) | Elluel (101050000)

maps = [101000000, 100000000, 102000000, 103000000,]
currentMap = sm.getFieldID()
if currentMap in maps:
    maps.remove(currentMap)

destString = "(If you don't have any more buisness in Elluel, you can move to other towns through the Mysterious Portal.) #b\r\n"
for index, option in enumerate(maps):
    destString += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
destination = sm.sendNext(destString)
sm.warp(maps[destination])