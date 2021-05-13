# Jake (1052006) | Subway Ticketing Booth (103020000)

kerningJQ = [690000041, 690000044, 690000047]

destString = "Would you like to enter the Subway Construction Site?\r\n"
for index, option in enumerate(kerningJQ):
    destString += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
jqIndex = sm.sendNext(destString)
sm.warp(kerningJQ[jqIndex])