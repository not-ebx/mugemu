# Ascendant

medal = 1142488
spell = 60001005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Ascendant> medal.")
    sm.giveSkill(spell)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)