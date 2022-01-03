# Ariant Private House2 (2103004) | Residential Area 2 (260000203)

sejan = 3929
food = 4031580

if sm.hasQuest(sejan):
    # Did the user just start the quest, or have they already dropped off some food elsewhere?
    sejanStatus = sm.getQRValue(sejan)
    sejanParse = "0000"
    houseIndex = 1
    if sejanStatus:
        sejanParse = sejanStatus
    
    # Check if the user already visited this house
    if sejanParse[houseIndex] != "3" and sm.hasItem(food):
        sm.consumeItem(food)

        # Check if this was the first house visited
        if not sejanStatus:
            sejanStatus = "0300"
        else:
            sejanStatus = sejanParse[:houseIndex] + "3" + sejanParse[houseIndex+1:]
        
        sm.setQRValue(sejan, sejanStatus, False)
        sm.sendSayOkay("You slowly placed the food on the floor.")
    elif not sm.hasItem(food) and sejanParse[houseIndex] != "3" and sejanStatus != "3333":
        sm.sendSayOkay("You do not have any more food. Forfeit the quest and talk to Sejan again.")
    else:
        sm.sendSayOkay("There's already food placed here.")
elif not sm.hasQuestCompleted(sejan):
    sm.sendSayOkay("This looks like a good spot to place some food.")