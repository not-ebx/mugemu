# Monarch's Power (24011)

elluelElf = 1142337

greatSpirit = 1033210

sm.setSpeakerID(greatSpirit)
if chr.getJob() == 2300:
    if sm.canHold(elluelElf):
        sm.sendNext("You Elves are in a heap of trouble with this curse. "
        "Deary, I'm counting on you to lead your people to victory!")
        sm.sendSay("You got a lot to do, love. "
        "Now, usually, you'd have to come see me again for your second and third tests, "
        "but I don't want to eat up any more of your time than I have to.")
        response = sm.sendAskYesNo("From now on, I'll come visit YOU when you're ready for the next test! "
        "Doesn't that sound like fun?")
        if response:
            sm.jobAdvance(2310)
            sm.giveItem(elluelElf)
            sm.completeQuest(parentID)
    else:
        sm.sendSayOkay("Deary, please make some room in your Equip inventory, okay?")
else:
    sm.sendSayOkay("Oh dear. Something's off with you, deary...")