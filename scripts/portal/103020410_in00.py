# Kerning Subway Line 2 Area 2 (103020410)

nextRequest = 2866

if sm.hasQuest(nextRequest):
    sm.warpInstanceIn(910310200, False)
    sm.setInstanceTime(300, 103020410, 3)
else:
    sm.warp(103020420)