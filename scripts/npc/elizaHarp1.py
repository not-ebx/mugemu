# Harp String <C> (2012027) | Eliza's Garden (920020000)
# Author: Tiger, Pyonta

sm.playSound("orbis/do", 100)

eternalSleep = 3114
songMaster = "CCGGAAGFFEEDDCGGFFEEDGGFFEEDCCGGAAGFFEEDDC"

if sm.hasQuest(eternalSleep):
    # Is this the first note?
    songStatus = sm.getQRValue(eternalSleep)
    songProgress = ""
    if songStatus:
        songProgress = songStatus
    
    songProgress += "C"
    songCount = len(songProgress)

    if not songProgress == songMaster[:songCount]:
        # Uh oh, you screwed up
        songProgress = ""
        sm.chat("The performance was a failure. Eliza seems very displeased.")
    
    # For C only: Is this the final note?
    if songProgress == songMaster:
        songProgress = "42"
        sm.chat("The performance was a success. Eliza breathed a sigh of relief.")
    
    sm.setQRValue(eternalSleep, songProgress, False)
