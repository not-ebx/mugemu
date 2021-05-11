# Ariant Private House2 (2103004) | Residential Area 2 (260000203)

sejan = 3929
food = 4031580

if sm.hasQuest(sejan):
    # Did the user just start the quest, or have they already dropped off some food elsewhere?
    sejanStatus = sm.getQRValue(sejan)
    placedFood = sejanStatus.isdecimal()
    sejanParse = 1
    if placedFood:
        sejanParse = int(sejanStatus)
    
    #Check if the user already visited this house
    if sejanParse % 3 != 0 and sm.hasItem(food):
        sm.consumeItem(food)
        #Check if this was the first house visited
        if not placedFood:
            sejanStatus = "3"
        else:
            sejanStatus = str(sejanParse * 3)
        
        #Will this be the last house visited?
        if sejanStatus == "210":
            sm.setQRValue(sejan, "3333", False)
        else:
            sm.setQRValue(sejan, sejanStatus)

        sm.sendSayOkay("You slowly placed the food on the floor.")
    elif not sm.hasItem(food) and sejanParse % 3 != 0 and sejanStatus != "3333":
        sm.sendSayOkay("You do not have any more #t" + str(food) + "#. Forfeit the quest and talk to Sejan again.")
    else:
        sm.sendSayOkay("There's already food placed here.")
elif not sm.hasQuestCompleted(sejan):
    sm.sendSayOkay("This looks like a good spot to place some food.")