# Forest of Training 4 (252010300) => SnowFro's Lair

chan = 9000075

stolenCamera = 3870

if not sm.hasQuest(stolenCamera) and not sm.hasQuestCompleted(stolenCamera):
    sm.setSpeakerID(chan)
    sm.sendNext("This is a forbidden area. Turn around.")
else:
    sm.warpInstanceIn(925120000, 4, False)
