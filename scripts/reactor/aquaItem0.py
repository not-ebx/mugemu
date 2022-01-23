# Middle Water Treasure Chest (2302000)

bubble = 2022040

reactor.incHitCount()
reactor.increaseState()

if reactor.getHitCount() >= 4:
	sm.dropItem(bubble, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
	sm.removeReactor()
