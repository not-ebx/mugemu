# Ardentmill (910001000) Exit

map = sm.getReturnField()
portal = sm.getReturnPortal()

# Henesys fallback
if map is None:
    map = 100000000
    portal = 19

sm.warpNoReturn(map, portal)