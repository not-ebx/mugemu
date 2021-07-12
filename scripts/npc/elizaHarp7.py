# Harp String <B> (2012033) | Eliza's Garden (920020000)
# Author: Tiger, Pyonta

sm.playSound("orbis/si", 100)

eternalSleep = 3114
songStatus = sm.getQRValue(eternalSleep)

if sm.hasQuest(eternalSleep) and songStatus != "42":
    # B is never used in the song, so...
    # Uh oh, you screwed up
    songProgress = ""
    sm.chat("The performance was a failure. Eliza seems very displeased.")
    sm.setQRValue(eternalSleep, songProgress, False)
