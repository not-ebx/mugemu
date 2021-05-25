# Superstar

medal = 1142499
inheritance = 60010217
spell = 60011005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Superstar> medal.")
    sm.giveSkill(inheritance)
    sm.giveSkill(spell)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
