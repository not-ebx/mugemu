# Child of the Goddess

medal = 1142635

if sm.canHold(medal):
    sm.chatScript("You obtained the <Child of the Goddess> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)