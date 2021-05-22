# Without a Trace

medal = 1142376

if sm.canHold(medal):
    sm.chatScript("You obtained the <Without a Trace> medal.")
    sm.startQuest(parentID)
    sm.completeQuest(parentID)