# Witchgrass (1072000) | The Swamp of Despair (103030000)

leaf = 4033042

reactor.incHitCount()

if reactor.getHitCount() >= 3:
	sm.dropItem(leaf, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
	sm.removeReactor()
