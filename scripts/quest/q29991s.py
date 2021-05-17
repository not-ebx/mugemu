# Ascendant

medal = 1142488
spell = 60001005

if sm.canHold(medal):
    sm.chatScript("You have earned a new medal.")
    sm.giveSkill(spell)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)