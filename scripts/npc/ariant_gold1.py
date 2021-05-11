# Ariant Private House1 Cupboard (2103009) | Residential Area 1 (260000202)

redScorpions = 3926
jewelry = 4031579

if sm.hasQuest(redScorpions):
    # Did the user just start the quest, or have they already dropped off some treasure elsewhere?
    jewelryStatus = sm.getQRValue(redScorpions)
    placedJewelry = jewelryStatus.isdecimal()
    jewelryParse = 1
    if placedJewelry:
        jewelryParse = int(jewelryStatus)
    
    #Check if the user already visited this house
    if jewelryParse % 2 != 0 and sm.hasItem(jewelry):
        sm.consumeItem(jewelry)
        #Check if this was the first house visited
        if not placedJewelry:
            jewelryStatus = "2"
        else:
            jewelryStatus = str(jewelryParse * 2)
        
        #Will this be the last house visited?
        if jewelryStatus == "210":
            sm.setQRValue(redScorpions, "3333", False)
        else:
            sm.setQRValue(redScorpions, jewelryStatus)

        sm.sendSayOkay("You carefully placed the treasure on the ground.")
    elif not sm.hasItem(jewelry) and jewelryParse % 2 != 0 and jewelryStatus != "3333":
        sm.sendSayOkay("You do not have any more #t" + str(jewelry) + "#. Forfeit the quest and return to the Red Scorpion's Lair.")
    else:
        sm.sendSayOkay("There's already treasure placed here.")
elif not sm.hasQuestCompleted(redScorpions):
    sm.sendSayOkay("This looks like a good place to drop the treasure.")