# Danger Zone Taxi (2023000)

destinationDict = {
    105000000: [105030000], # Sleepywood
    105030000: [105000000], # Another Door
    211000000: [211060000, 211040200, 211041400, 300000100], # El Nath
    211060000: [211000000], # Desolate Moor
    220000000: [220050300, 300000100], # Ludibrium
    220050300: [220000000], # Path of Time
    240000000: [240030000, 240040500], # Leafre
    300000100: [211000000, 220000000], # Small Forest
    }

currentMap = sm.getFieldID()
optionList = destinationDict[currentMap]

if len(optionList) > 1:
    destString = "Where would you like to go?\r\n"
    for index, option in enumerate(optionList):
        destString += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
    destIndex = sm.sendNext(destString)
    sm.warp(optionList[destIndex])
else:
    destination = optionList[0]
    response = sm.sendAskYesNo("Would you like to go to #m" + str(destination) + "m#?")
    if response:
        sm.warp(destination)
