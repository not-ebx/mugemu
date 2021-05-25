# The Power of Crystals (25511) | Luminous 3rd Job

vieren = 1032209

if chr.getJob() == 2710:
    sm.setSpeakerID(vieren)
    sm.sendNext("Luminous, I've gathered the power of all the Auguries.")
    sm.sendSay("I'll use this power to melt the Dark right out of you.")
    sm.sendSay("Remember, it's up to you to conquer your darkness. "
    "The Auguries will only help so much.")

    sm.setPlayerAsSpeaker()
    sm.sendSay("Have faith. I won't let the Dark take me again!")
            
    sm.setSpeakerID(vieren)
    sm.sendSay("Focus on this saying: "
    "#b<The light shines brightest in the deepest dark.> "
    "#kOkay, here we go!")

    sm.setPlayerAsSpeaker()
    sm.sendSay("AAAUGH!")

    sm.setSpeakerID(vieren)
    sm.sendSay("You did it! That wasn't so bad, was it?")

    sm.setPlayerAsSpeaker()
    sm.sendSay("(What is this new energy that courses through my body? "
    "It's as though the Light and Dark merged into one...)")

    sm.setSpeakerID(vieren)
    sm.sendSay("You should rest up for now. We can talk later.")

    sm.jobAdvance(2711)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)