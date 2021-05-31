# Ensorcelled Knights (20411) | Mihile 4th Job

from net.swordie.ms.enums import InvType

chief = 1142402
justiceShield = 1098003

neinhart = 1101002

sm.setSpeakerID(neinhart)
sm.sendNext("Cygnus is safe and the knights will be back to normal soon. "
"I've even heard some of them referring to you as the new Chief Knight. "
"It looks like you have no choice but to take up my proposal.")
if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
    if chr.getJob() == 5111:
        sm.jobAdvance(5112)
        sm.completeQuest(parentID)
        sm.giveItem(chief)
        sm.giveAndEquip(justiceShield)
    else:
        sm.sendSayOkay("You are not a 3rd job Mihile.")
else:
    sm.sendSayOkay("Please make room in your Equip inventory.")