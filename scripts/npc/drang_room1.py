# Parwen (2111006) | Authorized Personnel Only (261020401)

parwenKnows = 3320
deLangPotion = 3354

dranLab = 926120200

if sm.hasQuest(parwenKnows) or sm.hasQuestCompleted(parwenKnows) and not sm.hasQuest(deLangPotion) and not sm.hasQuestCompleted(deLangPotion):
    response = sm.sendAskYesNo("Are you ready to visit #m" + repr(dranLab) + "#?")
    if response:
        sm.warpInstanceIn(dranLab, 0)
else:
    if sm.hasQuest(deLangPotion) or sm.hasQuestCompleted(deLangPotion):
        sm.sendSayOkay("You really don't need to see that alchemist again, do you?")
    else:
        sm.sendSayOkay("You're not ready for this yet.")