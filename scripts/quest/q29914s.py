# King Pirate

medal = 1142013
echo = 1005

if sm.canHold(medal):
    sm.chatScript("You obtained the <King Pirate> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
