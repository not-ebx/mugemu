# Deep Sea Treasure Chest (2302001)

carta = 3076
seaDust = 4031251

reactor.incHitCount()
reactor.increaseState()

if reactor.getHitCount() >= 4:
	if sm.hasQuest(carta):
	    sm.dropItem(seaDust, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
	sm.removeReactor()
