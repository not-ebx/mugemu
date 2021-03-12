# Knight Stronghold: Hall of Honor
# Quest: Cygnus Garden

CYGNUS_GARDEN_QUEST = 31149
CYGNUS_GARDEN = 271040000

if sm.hasQuestCompleted(CYGNUS_GARDEN_QUEST):
    sm.warp(CYGNUS_GARDEN, 5)
elif sm.hasQuest(CYGNUS_GARDEN_QUEST):
    sm.chat("The Cygnus Garden has been located.")
    sm.setQRValue(CYGNUS_GARDEN_QUEST, "find", False)    
else:
    sm.dispose()
