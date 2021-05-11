palaceToll = 3901
palacePass = 4031582

if sm.hasItem(palacePass):
    sm.chat("I have gained entrance to the palace.")
    sm.warp(260000301)
else:
    sm.chat("Those who have not received the permit cannot enter the palace.")
    #Quick quest re-enable if you lose the pass
    sm.deleteQuest(palaceToll)