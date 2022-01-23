# Ariant Private House2 Cupboard (2103010) | Residential Area 2 (260000203)

redScorpions = 3926
jewelry = 4031579

if sm.hasQuest(redScorpions):
    # Did the user just start the quest, or have they already dropped off some treasure elsewhere?
    jewelryStatus = sm.getQRValue(redScorpions)
    jewelryParse = "0000"
    houseIndex = 1
    if jewelryStatus:
        jewelryParse = jewelryStatus
    
    # Check if the user already visited this house
    if jewelryParse[houseIndex] != "3" and sm.hasItem(jewelry):
        sm.consumeItem(jewelry)

        # Check if this was the first house visited
        if not jewelryStatus:
            jewelryStatus = "0300"
        else:
            jewelryStatus = jewelryParse[:houseIndex] + "3" + jewelryParse[houseIndex+1:]
        
        sm.setQRValue(redScorpions, jewelryStatus, False)
        sm.sendSayOkay("You carefully placed the treasure on the ground.")
    elif not sm.hasItem(jewelry) and jewelryParse[houseIndex] != "3" and jewelryStatus != "3333":
        sm.sendSayOkay("You do not have any more treasure. Forfeit the quest and return to the Red Scorpion's Lair.")
    else:
        sm.sendSayOkay("There's already treasure placed here.")
elif not sm.hasQuestCompleted(redScorpions):
    sm.sendSayOkay("This looks like a good place to drop the treasure.")