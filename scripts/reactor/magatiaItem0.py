# Snow Rose Garden (2612000) | Where Snow Rose Grows (926120300)
# TO DO: Get the reactor to detect if there's May Mist dropped near it, and consume it to transform the reactor into its hittable state

mayMist = 4000361
snowRose = 4031695

reactor.incHitCount()

if reactor.getHitCount() >= 3:
    sm.dropItem(snowRose, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
    sm.removeReactor()