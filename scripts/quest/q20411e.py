# Ensorcelled Knights (20411) | Mihile 4th Job

sm.setSpeakerID(1101002)
sm.sendNext("Cygnus is safe and the knights will be back to normal soon. I've even heard some of them referring to you as the new Chief Knight. It looks like you have no choice but to take up my proposal.")
if sm.canHold(1142402):
    if chr.getJob() == 5111:
        sm.jobAdvance(5112)
        sm.giveItem(1142402)
        sm.completeQuest(parentID)
    else:
        sm.sendSayOkay("You are not a 3rd job Mihile.")
else:
    sm.sendSayOkay("Please make room in your Equip inventory.")
    