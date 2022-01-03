# Camel Cab (2110005)

destinationDict = {
    260000000: 261000000, # Ariant
    260020000: 261000000, # Outside North Entrance of Ariant
    261000000: 260000000, # Magatia
    260020700: 260000000, # Sahel 1
}

currentMap = sm.getFieldID()
destination = destinationDict[currentMap]

response = sm.sendAskYesNo("Do you want to go to #m" + repr(destination) + "#?")
if response:
    sm.warp(destination)
