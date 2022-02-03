# Sunburst (3862) |  Part of the Golden Temple Questline

gateOfTrials = 9000098

ravanaAltarEntrance = 252030000

sm.setSpeakerID(gateOfTrials)
sm.sendNext("#b(A voice resonates in your head again.) \r\n"
            "'The Sacrifice will protect thee, but it shall be destroyed the moment thou set foot in front of the golden altar! "
            "If thou dost not enter, the sacrifice will not be taken.' \r\n"
            "(Massive doors open the way to the #m" + repr(ravanaAltarEntrance) + "#.)")

sm.warp(ravanaAltarEntrance) # Entrance to Ravana's Altar
sm.completeQuest(parentID)
