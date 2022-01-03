# A Ghost's Perspective (2868)

jane = 1052105
lineTwo = 103020420
shade = 5090000

sm.setSpeakerID(jane)
sm.sendNext("What is it...?")

sm.setPlayerAsSpeaker()
sm.sendNext("Jane, I found this hat in the subway. Do you think a ghost might be involved?")

sm.setSpeakerID(jane)
response = sm.sendAskAccept("A ghost...? I find that hard to believe..."
"but I guess I could help you look for a ghost if you show me where you found that hat.\r\n"
"#b#e (You will be moved to #m" + repr(lineTwo) + "# upon accepting this quest)")
if response:
    sm.startQuest(parentID)
    sm.sendNext("Ok...Oh, make sure you deal with the #o" + repr(shade) + "#s while I'm concentrating. "
    "About 100 should be enough...")
    sm.warp(lineTwo)