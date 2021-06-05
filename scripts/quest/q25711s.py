# [Job Advancement] Final Kaiser Awakening! (25711) | Kaiser 3rd Job

from net.swordie.ms.enums import InvType

awakened = 1142486
justiceEssence = 1352502

sm.flipDialoguePlayerAsSpeaker()
if chr.getJob() == 6110:
    response = sm.sendAskYesNo("I'm full up on Nova Essence. Should I try my Final Awakening? "
    "If I succeed, I'll get my final form. I can do this...right?")
    if response:
        if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
            sm.jobAdvance(6111)
            sm.startQuest(parentID)
            sm.completeQuest(parentID)
            sm.giveItem(awakened)
            sm.giveAndEquip(justiceEssence)
            sm.sendNext("YES! I can transform into Kaiser's final form, "
            "and completely wreck my enemies with even more moves.")
        else:
            sm.sendSayOkay("I should make some room in my Equip inventory.")
else:
    sm.sendSayOkay("You're currently not a second job Kaiser.")