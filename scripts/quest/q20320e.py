# Knight's Qualification Exam (20320) | Mihile 3rd Job

sm.setSpeakerID(1101002)
response = sm.sendAskYesNo("Now you're a REAL knight. Would you like to take your Job Advancement?")
if response:
    if sm.canHold(1142401):
        if chr.getJob() == 5110:
            sm.jobAdvance(5111)
            sm.giveItem(1142401)
            sm.completeQuest(parentID)
        else:
            sm.sendSayOkay("You are not a 2nd job Mihile.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")
    