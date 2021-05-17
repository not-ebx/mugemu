# Scrap Iron (1032002)

pickall = 2855
scrapIron = 4033037

if sm.hasQuest(pickall):
    sm.dropItem(4033037, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
sm.invokeAfterDelay(200, "removeReactor")