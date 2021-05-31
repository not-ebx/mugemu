# [Job Advancement] Break Nova Essence Limit (25710) | Kaiser 2nd job advancement

from net.swordie.ms.enums import InvType

fated = 1142485
guardianEssence = 1352501

sm.flipDialoguePlayerAsSpeaker()
if chr.getJob() == 6100:
    response = sm.sendAskYesNo("Woah, this is what happens when I beat up my enemies? "
    "I'm pumped up with this Nova Essence stuff. I might be able to push my limit further... "
    "I should try that, yeah?")
    if response:
        if sm.getEmptyInventorySlots(InvType.EQUIP) >= 2:
            sm.jobAdvance(6110)
            sm.completeQuest(parentID)
            sm.giveItem(fated)
            sm.giveAndEquip(guardianEssence)
            sm.sendNext("Yeah! I can feel my limit growing greater! "
            "With this much Essence, I can probably use more new skills. "
            "I should keep pushing my limits and growing my awesome powers.")
        else:
            sm.sendSayOkay("I should make some room in my Equip inventory.")
else:
    sm.sendSayOkay("You're currently not a first job Kaiser.")