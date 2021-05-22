# Waist Deck [Phantom Tutorial: Skip] (915000100)

ghostwalk = 20031211
chump = 20031212
shroudwalk = 20031205

MAPLE_ADMINISTARTOR = 2007
medal = 1142375

tutorial = 25000
lumiere = 150000000

sm.setSpeakerID(MAPLE_ADMINISTARTOR)

if sm.sendAskYesNo("Would you like to skip the tutorial and instantly arrive at the #m" + str(lumiere) + "#?"):
    # Swap beginner skills
    sm.removeSkill(ghostwalk)
    sm.removeSkill(chump)
    sm.giveSkill(shroudwalk)

    # Let PTjob1.py handle everything else
    sm.warp(lumiere)