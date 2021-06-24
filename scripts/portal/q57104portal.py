# Unfamiliar Hillside (807040000) => Momijigaoka Entrance
# Momijigaoka Entrance => Momijigaoka

entrance = 807040100
momijigaoka = 807000000

# Hayato
newBase = 57104
# Kanna
freshStart = 57402

if sm.getFieldID() == entrance:
    if sm.hasQuestCompleted(newBase) or sm.hasQuestCompleted(freshStart):
        sm.warp(momijigaoka, 1)
    else:
        sm.chat("Speak with the warrior at the entrance.")
else:
    if sm.hasQuest(newBase) or sm.hasQuest(freshStart):
        sm.warp(entrance)
    else:
        sm.chat("Speak with the warrior on the left first.")
