# Elven Noble

medal = 1142338

if sm.canHold(medal):
    sm.chatScript("You obtained the <Elven Noble> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)