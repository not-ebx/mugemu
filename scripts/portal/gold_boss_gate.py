# Abandoned Temple (252020000) => Room of Suffering

demonMostPeculiar = 3861

if sm.hasQuest(demonMostPeculiar) or sm.hasQuestCompleted(demonMostPeculiar):
    sm.warp(252020700, 2)
else:
    sm.chat("Corrupted energy from the temple's depths prevents you from advancing further.")