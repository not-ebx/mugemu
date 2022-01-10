# Ardentmill (910001000) Exit

map = sm.getReturnField()
portal = sm.getReturnPortal()

# Henesys fallback
if map in (0, 910000000):
    sm.chat("(Portal) Cannot find your previous map ID, warping to Henesys.")
    map = 100000000
    portal = 19

sm.warpNoReturn(map, portal)