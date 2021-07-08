# Isa the Station Guide (2012006) | Orbis Station Entrance (200000100)

maps = [200000120, 200000130, 200000141, 200000110, 200000150, 200000161, 200000170]

list = "Hey, where would you like to go? #b\r\n"
for index, option in enumerate(maps):
    list += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
answer = sm.sendNext(list)
sm.warp(maps[answer])
