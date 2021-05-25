# Ariant Treasure Vault (915020101)
if sm.hasQuest(25111):
    sm.lockInGameUI(True)
    sm.removeEscapeButton()
    sm.flipDialoguePlayerAsSpeaker()
    sm.sendNext("Reminds me of old times... that picture in the frame. "
    "I sure hated it when I was younger. I still kind of hate it.")
    sm.sendSay("No time for swimming around in dark old thoughts. "
    "That skill book should be in this box.")
    sm.lockInGameUI(False)