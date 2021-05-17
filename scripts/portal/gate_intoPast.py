# Three Doors (270000000)
# TO DO: Populate pastDest based on ToT questline progress

pastDest = [270010000, 270020000, 270030000, 270050000]
johanna = 2140004

if not sm.hasQuestCompleted(3500): # time lane quest
    sm.chat("You do not have the Goddess' permission to enter through the Door to the Past.")
else:
    sm.setSpeakerID(johanna)
    destString = "Where would you like to go?\r\n"
    for index, option in enumerate(pastDest):
        destString += "#L"+ str(index) + "##m" + str(option) + "##l\r\n"
    destIndex = sm.sendNext(destString)
    sm.warp(pastDest[destIndex])
