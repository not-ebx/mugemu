# True Knight of Light

medal = 1142403

if sm.canHold(medal):
    sm.chatScript("You obtained the <True Knight of Light> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)