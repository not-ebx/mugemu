# Elf of Elluel

medal = 1142337

if sm.canHold(medal):
    sm.chatScript("You obtained the <Elf of Elluel> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)