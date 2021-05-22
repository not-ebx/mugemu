# A Hero, No More (Phantom)

medal = 1142375

if sm.canHold(medal):
    sm.chatScript("You obtained the <A Hero, No More> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)