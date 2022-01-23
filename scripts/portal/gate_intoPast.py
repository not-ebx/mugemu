# Three Doors (270000000)

pastList = [
    (270010000, 3500), # Past of the Verdure / Path to the Past
    (270020000, 3507), # Frozen Past / Memory Keeper
    (270030000, 3514), # Burning Past / The Sorcerer Who Sells Emotions
    (270040000, 3519) # Broken Corridor / The One Who Walks Through the Road to Oblivion 5
]
johanna = 2140004

if not sm.hasQuestCompleted(pastList[0][1]):
    sm.chat("You do not have the Goddess' permission to enter through the Door to the Past.")
else:
    # Determine user's cutoff point based on ToT questline progress
    pastEnd = 0
    for sector in pastList:
        if not sm.hasQuestCompleted(sector[1]):
            break
        pastEnd += 1
    
    # Go straight to Past of the Verdure if questline has not been completed up to Memory Keeper
    if pastEnd == 1:
        sm.warp(pastList[0][0])
    else:
        # Slice up to appropriate cutoff
        filteredPast = pastList[:pastEnd]

        sm.setSpeakerID(johanna)
        destString = ["I can instantly warp those with the proper permissions around the Temple. Where would you like to go? #b\r\n"]
        for index, option in enumerate(filteredPast):
            destString.append(''.join(["#L", repr(index), "##m", repr(option[0]), "##l\r\n"]))
        destIndex = sm.sendNext(''.join(destString))
        sm.warp(filteredPast[destIndex][0])
