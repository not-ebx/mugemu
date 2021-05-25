# The Middle Road (25510) | Luminous 2nd Job

prism = 2430874
vieren = 1032209

if chr.getJob() == 2700:
    if sm.canHold(prism) or sm.hasItem(prism):
        sm.setSpeakerID(vieren)
        sm.sendNext("Luminous! Snap out of it, will you? "
        "I'll resonate with the power of Light and draw the Dark out of you. "
        "Maybe then you'll come to your senses.")

        sm.setPlayerAsSpeaker()
        sm.sendSay("(Vieren's voice seems to be calming me. Peculiar.)")
        sm.sendSay("The Dark no longer clouds my mind. You have my thanks.")

        # One prism per character, and in case of dialogue flipping
        if not sm.hasItem(prism):
            sm.giveItem(prism)

        sm.setSpeakerID(vieren)
        sm.sendSay("It was nothing. "
        "All I did was help you find the strength to control your Dark. "
        "Here, this #t" + str(prism) + "# will let you come and go as you please.")

        sm.jobAdvance(2710)
        sm.startQuest(parentID)
        sm.completeQuest(parentID)
    else:
        sm.sendSayOkay("Please check if you have space in your Use inventory.")