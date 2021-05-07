palaceToll = 3901
palacePass = 4031582

if sm.hasItem(palacePass):
    sm.warp(260000301)
else:
    sm.chat("You need an Entry Pass to enter the palace.")
    #Quick quest re-enable after giving the pass to Jiyur/dropping it
    sm.deleteQuest(palaceToll)