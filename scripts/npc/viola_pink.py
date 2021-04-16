# Forest of Tenacity: Stage 2
# Quest: John's Pink Flower Basket

SLEEPYWOOD = 105000000
PINK_FLOWER = 1063000
PINK_VIOLA = 4031025 # quest item
JOHNS_PINK = 2052 # quest
MIN_DIST = 225

sm.setSpeakerID(PINK_FLOWER)
if abs(sm.getObjectPositionX() - (sm.getChr().getPosition().getX())) > MIN_DIST: # we check for distance between player and flower
    sm.sendSayOkay("You can't see the inside of the pile of flowers very well because"
                    "\r\nyou're too far. Go a little closer.")
else:
    if sm.hasQuest(JOHNS_PINK):
        if sm.canHold(PINK_VIOLA):
            sm.giveItem(PINK_VIOLA, 10)
        else:
            sm.sendSayOkay("Please make more space in your ETC inventory.")
            sm.dispose()
    sm.warp(SLEEPYWOOD)