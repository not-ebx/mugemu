# Isa the Station Guide (2012006) | Orbis Station Entrance (200000100)

maps = [200000110, 200000120, 200000130, 200000140, 200000150, 200000160, 200000170]

destString = ["Hey, where would you like to go? #b\r\n"]
for index, option in enumerate(maps):
    destString.append(''.join(["#L", repr(index), "##m", repr(option), "##l\r\n"]))
destination = sm.sendNext(''.join(destString))
sm.warp(maps[destination])