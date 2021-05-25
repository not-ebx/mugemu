# Treasure Chest (2159461) | Ariant Treasure Vault (915020101)
# Phantom 3rd job adv

ravenMind = 1142377

lowdown = 25111

if sm.hasQuest(lowdown):
    if sm.canHold(ravenMind):
        sm.giveItem(ravenMind)
        sm.completeQuestNoRewards(lowdown)
        sm.jobAdvance(2411)
        sm.addMaxHP(300)
        sm.addMaxMP(150)

        sm.removeEscapeButton()
        sm.setPlayerAsSpeaker()
        sm.sendNext("I have really got to get myself organized. "
        "Half of this stuff should have gone in a refrigerator... "
        "Oh, wait! There it is. It's time for a job advancement!")
    else:
        sm.chat("Please make room in your Equip inventory.")
else:
    sm.chat("You are not 2nd job Phantom.")
