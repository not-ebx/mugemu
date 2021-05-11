#TO DO: Implement The Secret, Quiet Passage scripts (3358, 3359, 3360)

zenumist = 261010000
alcadno = 261020200
secretPassage = 261030000

if sm.getFieldID() == zenumist:
    sm.warp(secretPassage, 2)
else:
    sm.warp(secretPassage, 1)