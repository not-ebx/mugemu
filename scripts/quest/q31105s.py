# Henesys Ruins: Henesys Ruins
# Quest: Henesys in Ruins

CHIEF_ALEX = 2142001

sm.removeEscapeButton()

sm.setSpeakerID(CHIEF_ALEX)
sm.sendNext("But where did you come from? Since Cygnus's attack,"
 "\r\nwe've heard nothing from the rest of the world.")

sm.setPlayerAsSpeaker()
sm.sendSay("(You can't exactly reveal that you're fromt he past...) Um, I"
"\r\nwas knocked out...yeah! I just woke up here. I, uh, can't remember what happened. Anyway, what's going on here?")

sm.setSpeakerID(CHIEF_ALEX)
sm.sendSay("Perhaps the horrors of war have taken your memory."
            "\r\nCygnus has been corrupted by the Black Mage, and her"
            "\r\nKnights have become our enemies. They attacked us,"
            "\r\nand...and father... I'm sorry, this is still too painful for me."
            "\r\nPlease ask Athena Pierce to tell you the rest.")
sm.setPlayerAsSpeaker()
sm.sendSay("Okay, I understand.")

sm.startQuest(parentID)