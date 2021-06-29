# Momijigaoka : Unfamiliar Hillside (807040000) | Hayato/Kanna Starting Map
# Author: Tiger, Pyonta

from net.swordie.ms.constants import JobConstants

yukimori = 9130031
kanetsugu = 9130082

# Check if character is Hayato or Kanna, and hide the other job's NPC
sengoku = chr.getJob()

if JobConstants.isKanna(sengoku):
    sm.hideNpcByTemplateId(yukimori, True)
else:
    sm.hideNpcByTemplateId(kanetsugu, True)

if sengoku == 4001:
    sm.lockInGameUI(True)
    sm.playVideoByScript("JPHayato.avi")
    sm.levelUntil(10)
    sm.jobAdvance(4100)
    sm.resetAP(False, 4100)
    sm.lockInGameUI(False)

elif sengoku == 4002:
    sm.lockInGameUI(True)
    sm.playVideoByScript("JPKanna.avi")
    sm.levelUntil(10)
    sm.jobAdvance(4200)
    sm.resetAP(False, 4200)
    sm.lockInGameUI(False)
