# Jake (1052006) | Subway Ticketing Booth (103020000)

kerningJQ = [690000041, 690000044, 690000047]

destString = ["Would you like to enter the Subway Construction Site?\r\n"]
for index, option in enumerate(kerningJQ):
    destString.append(''.join(["#L", repr(index), "##m", repr(option), "##l\r\n"]))
jqIndex = sm.sendNext(''.join(destString))
sm.warp(kerningJQ[jqIndex])