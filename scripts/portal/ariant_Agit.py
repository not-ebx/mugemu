sejan = 3930
ardin = 3933
eleska = 3936
emptyHouse = 260000201

if sm.hasQuestCompleted(sejan) and sm.hasQuestCompleted(ardin) and sm.hasQuestCompleted(eleska):
    sm.warp(emptyHouse)
else:
    sm.chat("Only members of the Sand Bandits may enter.")