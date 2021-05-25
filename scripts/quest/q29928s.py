# Aran the Hero

medal = 1142133
echo = 20001005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Aran the Hero> medal.")
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)