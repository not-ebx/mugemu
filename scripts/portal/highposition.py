# The One Who's Touched the Sky medal quest script

highSpotDict = {
    101020300: "ellinia", # Chimney Tree Top
    102000000: "perion", # Perion
    103000000: "kerning", # Kerning City
    120000000: "nautilus", # Nautilus Harbor
    200080100: "orbis", # Entrance to Orbis Tower
}

touchedSky = 29004
# This dummy quest outputs how many spots were found
skyTracker = 27018

medalStatus = sm.getQRValue(touchedSky)
currentMap = sm.getFieldID()
mapCap = "5"
medalName = "The One Who's Touched the Sky"

# Automatically accept The One Who's Touched the Sky if it's not active and unfinished after touching a spot
# This will also initialize the dummy quest
if not sm.hasQuest(touchedSky) and not sm.hasQuestCompleted(touchedSky):
    sm.startQuest(touchedSky)
    sm.createQuestWithQRValue(skyTracker, "0")

# Don't run the script if the medal has been claimed
if not sm.hasQuestCompleted(touchedSky):
    skyStatus = sm.getQRValue(skyTracker)

    # Another contingency check to initialize the dummy quest if the user (re-)started the medal quest through the Medal UI
    if medalStatus == "" and not skyStatus == "0" or not sm.hasQuest(skyTracker):
        sm.createQuestWithQRValue(skyTracker, "0")

    if currentMap in highSpotDict:
        # Is this the first time that spot has been visited?
        areaQR = highSpotDict[currentMap]
        if areaQR not in medalStatus and not medalStatus == mapCap:
            updateStatus = int(skyStatus) + 1
            sm.setQRValue(skyTracker, repr(updateStatus), False)
            sm.addQRValue(touchedSky, areaQR)

            # Was that the last spot visited?
            if repr(updateStatus) == mapCap:
                sm.setQRValue(touchedSky, mapCap, False)
                sm.chatScript("Earned " + medalName + " title!")
            else:
                sm.chatScript(''.join([repr(updateStatus), "/", mapCap, " Regions Completed"]))
                sm.chatScript(medalName + " title in progress.")