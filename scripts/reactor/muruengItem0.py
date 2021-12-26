# Mu Lung Peach [left-facing] (2502000)

peach = 2022116

reactor.incHitCount()

if reactor.getHitCount() >= 3:
	sm.dropItem(peach, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
	sm.removeReactor()
