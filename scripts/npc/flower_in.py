# Mysterious Statue, Sleepywood (105000000)

STAGE_1 = 910530000
STAGE_3 = 910530100
STAGE_5 = 910530200

JOHNS_PINK = 2052
JOHNS_PRESENT = 2053
JOHNS_LAST = 2054

response = sm.sendAskYesNo("Once I lay my hand on the statue, a strange light covers me and it feels like I'm being sucked "
                           + "into somewhere else. Is it okay to be moved to somewhere else randomly just like that?")

if response:
    if sm.hasQuestCompleted(JOHNS_PRESENT):
        sm.warp(STAGE_5)
    elif sm.hasQuestCompleted(JOHNS_PINK):
        sm.warp(STAGE_3)
    else:
        sm.warp(STAGE_1)