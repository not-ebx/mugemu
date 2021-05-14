# Old Man's House (200050001) => Cloud Park IV

nependeathJuice = 3048
foundHella = 31034
syrup = 4031201

if sm.hasQuest(nependeathJuice) and sm.hasQuest(foundHella) or sm.hasQuestCompleted(foundHella):
    if sm.canHold(syrup) and not sm.hasItem(syrup):
        sm.chat("Hella sneaks some Sweet Syrup into your inventory on the way out.")
        sm.giveItem(syrup)
    elif not sm.canHold(syrup):
        sm.chat("Hella made a movement before stopping herself. Check your ETC. inventory for space.")

sm.warp(200050000, 9)