# 20810 - [Job Adv] (Lv.30)   Mihile

sm.setSpeakerID(2520025)
response = sm.sendAskYesNo("Congratulations on passing your trials, Mihile. "
"Are you prepared to be declared an official apprentice knight?")
if response:
    if sm.getEmptyInventorySlots(1) >= 2:
        if chr.getJob() == 5100:
            sm.jobAdvance(5110)
            sm.giveItem(1302038)
            sm.giveItem(1142400)
            sm.completeQuest(parentID)
            sm.sendSayOkay("I now pronounce you an Apprentice Knight! "
            "I've given you some SP to use. Make us proud.")
        else:
            sm.sendSayOkay("You are not a 1st job Mihile.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")