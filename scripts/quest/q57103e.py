# Picking up the Pieces (57103)

yukimori = 9130024

sm.setSpeakerID(yukimori)
sm.sendNext("This place appears to be our land, but do you feel it? The air is too sweet, the ground too soft.")

sm.setPlayerAsSpeaker()
sm.sendNext("But it looks like Japan, like our home.")

sm.setSpeakerID(yukimori)
sm.sendNext("I too believed this place to be Japan, but I assure you it is not. We are in a different world. "
"Whatever that strange light was at Honnou-ji, it has sent us somewhere we could never have imagined.")

sm.setPlayerAsSpeaker()
sm.sendNext("This sounds preposterous, yet...")

sm.setSpeakerID(yukimori)
response = sm.sendAskYesNo("I will take you to the others. We have established a temporary base of operations, "
"hidden in the forest at the top of this hill. You will see that I speak the truth.")
if response:
    sm.completeQuest(parentID)
else:
    sm.sendNext("Speak to me again after you wish to enter our base of operations.")
