# The One Who's Touched the Sky medal quest script

highSpotDict = {
    101020300: "ellinia", # Chimney Tree Top
    102000000: "perion", # Perion
    103000000: "kerning", # Kerning City
    120000000: "nautilus", # Nautilus Harbor
    200080100: "orbis", # Entrance to Orbis Tower
}

# This dummy quest outputs how many spots were found
skyTracker = 27018
touchedSky = 29004

finalStatus = sm.getQRValue(touchedSky)
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

    # Another contingency check to initialize the dummy quest if the user (re-)started it through the Medal UI
    if finalStatus == "" and not skyStatus == "0" or not sm.hasQuest(skyTracker):
        sm.createQuestWithQRValue(skyTracker, "0")

    # Check if currentMap actually exists in the dict first
    if currentMap in highSpotDict:
        # Check if this is the first time this spot has been visited
        areaQR = highSpotDict[currentMap]
        if areaQR not in finalStatus and not finalStatus == mapCap:
            updateStatus = int(skyStatus) + 1
            sm.setQRValue(skyTracker, str(updateStatus), False)
            sm.addQRValue(touchedSky, areaQR)
            if str(updateStatus) == mapCap:
                sm.setQRValue(touchedSky, mapCap, False)
                sm.chatScript("Earned " + medalName + " title!")
            else:
                sm.chatScript(str(updateStatus) + "/" + mapCap + " Regions Completed")
                sm.chatScript(medalName + " title in progress.")