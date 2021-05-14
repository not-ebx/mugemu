# Audrey (9201135)

maps = [540000000, 541000000, 550000000, 551000000]
currentMap = sm.getFieldID()
if currentMap in maps:
    maps.remove(currentMap)

destString = "Where would you like to go?\r\n"
for index, option in enumerate(maps):
    destString += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
destination = sm.sendNext(destString)
sm.warp(maps[destination])