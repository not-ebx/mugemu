# Queen's Treasure Box (2602000) | Treasure Room of Queen (926000010)

eleska = 3935
skyJewel = 4031574

reactor.incHitCount()

if reactor.getHitCount() >= 3:
	if not sm.hasItem(skyJewel):
	    sm.dropItem(skyJewel, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
	sm.removeReactor()
