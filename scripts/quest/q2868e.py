# A Ghost's Perspective (2868)

jane = 1052105
naora = 103000004
shade = 5090000

sm.setPlayerAsSpeaker()
sm.sendNext("Did you find anything?")

sm.setSpeakerID(jane)
sm.sendNext("I didn't find any ghosts of the hat's owner..."
"but it was dropped by one of the #o" + str(shade) + "#s here.")

sm.setPlayerAsSpeaker()
sm.sendNext("(What exactly happened to the hat's owner down here? "
"Maybe we should return to the hospital for now...)")
sm.completeQuest(parentID)
sm.warp(naora)