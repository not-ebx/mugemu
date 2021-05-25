# Leafre Treasure Vault Entrance (915020200)

guardiosoMob = 9001047

guardioso = 2159462

if sm.hasQuest(25121):
    sm.lockInGameUI(True)
    sm.forcedInput(2)
    sm.sendDelay(1000)
    sm.forcedInput(0)

    sm.removeEscapeButton()
    sm.flipDialoguePlayerAsSpeaker()
    sm.sendNext("(I spent a fortune on that Guardioso, but it seems to have warded off any would-be poachers.)")
    sm.sendSay("Open the door.")

    sm.setSpeakerID(guardioso)
    sm.sendSay("Voice... check...")

    sm.flipDialoguePlayerAsSpeaker()
    sm.sendSay("I thought you were faster. Are you getting rusty?")

    sm.setSpeakerID(guardioso)
    sm.sendSay("Intruder! Intruder! Shifting to battle mode! Destroy the intruder!")

    sm.flipDialoguePlayerAsSpeaker()
    sm.sendSay("W-what? Hey, what's wrong with you?! I own you!")

    sm.setSpeakerID(guardioso)
    sm.sendSay("Intruder elimination in progress!")

    sm.flipDialoguePlayerAsSpeaker()
    sm.sendSay("Hey! Stop it!")

    sm.setSpeakerID(guardioso)
    sm.sendSay("ELIMINATE!")

    sm.removeNpc(guardioso)
    sm.spawnMob(guardiosoMob, 283, 182, False)
    sm.lockInGameUI(False)