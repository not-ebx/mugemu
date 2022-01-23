# Lost Memories Found [Hayato] (57172)

recoveredMemory = 7081

mouriMotonari = 9130008

sm.setSpeakerID(mouriMotonari)
sm.sendNext("I've been watching you fight for Maple World, Hayato. "
"Your dedication is impressive.")
sm.sendSay("I, Mouri Motonari, hope that you will call me an ally. "
"The two of us have a great future together.")
sm.sendSay("Continue your quest, and I shall ensure we go down in history.")

sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.startQuest(recoveredMemory)
sm.setQRValue(recoveredMemory, "1", False)