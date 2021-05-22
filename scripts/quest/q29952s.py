# A Hero, No More (Mercedes)

medal = 1142336

if sm.canHold(medal):
    sm.chatScript("You obtained the <A Hero, No More> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)