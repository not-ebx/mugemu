# Forest of Training 4 (223010100) => Abandoned Temple

chan = 9000075

loongAbandoned = 3856

if sm.hasQuest(loongAbandoned) or sm.hasQuestCompleted(loongAbandoned):
    sm.warp(252020000, 7)
else:
    sm.setSpeakerID(chan)
    sm.sendSayOkay("Halt! It's too dangerous for any tourists to go deeper into the temple. Turn around, now.")
