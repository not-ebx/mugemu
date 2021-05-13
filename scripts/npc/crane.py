# Crane (2090005)

destinationDict = {
    200000141: [200090300], # Orbis Cabin
    250000100: [200090310, 251000000, 252000000], # Mu Lung
    251000000: [250000100], # Herb Town
    }

currentMap = sm.getFieldID()
optionList = destinationDict[currentMap]

if sm.getFieldID() == 250000100:
    destination = sm.sendNext("Where would you like to go?#b\r\n"
    "#L0# Orbis #l\r\n"
    "#L1# Herb Town #l\r\n"
    "#L2# Golden Temple #l\r\n")
    sm.warp(optionList[destination])
else:
    response = sm.sendAskYesNo("Would you like to go to Mu Lung?")
    if response:
        sm.warp(optionList[0])