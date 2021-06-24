# Portrait (1403003) | Leafre Treasure Vault (915010201)
# Phantom 4th Job

remembers = 1142378

empress = 25122

if sm.hasQuest(empress):
    if sm.canHold(remembers):
        sm.giveItem(remembers)
        sm.completeQuestNoRewards(empress)
        sm.jobAdvance(2412)
        sm.addMaxHP(300)
        sm.addMaxMP(150)

        sm.removeEscapeButton()
        sm.setPlayerAsSpeaker()
        sm.sendNext("You always wore that silly little smile "
        "to make your advisors think everything was alright. "
        "You always were too concerned about everybody else... "
        "And now you give me the skill book I was looking for. What a dear.")
    else:
        sm.chat("Please make room in your Equip inventory.")
else:
    sm.chat("You are not 3rd job Phantom.")
