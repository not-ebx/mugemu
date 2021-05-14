# Harp String <F> (2012030) | Eliza's Garden (920020000)
# Author: Tiger, Pyonta

sm.playSound("orbis/pa", 100)

eternalSleep = 3114
songMaster = "CCGGAAGFFEEDDCGGFFEEDGGFFEEDCCGGAAGFFEEDDC"

if sm.hasQuest(eternalSleep):
    # Is this the first note?
    songStatus = sm.getQRValue(eternalSleep)
    songProgress = ""
    if songStatus:
        songProgress = songStatus
    
    songProgress += "F"
    songCount = len(songProgress)

    if not songProgress == songMaster[:songCount]:
        # Uh oh, you screwed up
        songProgress = ""
        sm.chat("The performance was a failure. Eliza seems very displeased.")
    
    sm.setQRValue(eternalSleep, songProgress, False)
