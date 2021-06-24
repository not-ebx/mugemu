# Castle Entrance (921110300) | Shade 3rd Job

SHADE_THIRD_JOB = 38074

if sm.hasQuest(SHADE_THIRD_JOB):
    sm.removeEscapeButton()
    sm.setPlayerAsSpeaker()
    sm.flipSpeaker()
    sm.sendNext("This castle used to be so grand, and now it's all in ruins. If I remember correctly there's a way in there somewhere.")
    sm.completeQuest(SHADE_THIRD_JOB)
