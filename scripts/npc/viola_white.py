# Forest of Tenacity: Stage 7
# Quest: John's Pink Flower Basket

SLEEPYWOOD = 105000000
WHITE_FLOWER = 1063002
WHITE_VIOLA = 4031028 # quest item
JOHNS_LAST = 2054 # quest
MIN_DIST = 275

sm.setSpeakerID(WHITE_FLOWER)
if abs(sm.getObjectPositionY() - (sm.getChr().getPosition().getY())) > MIN_DIST: # we check for distance between player and flower
    sm.sendSayOkay("You can't see the inside of the pile of flowers very well because"
                    "\r\nyou're too far. Go a little closer.")
else:
    if sm.hasQuest(JOHNS_LAST):
        sm.giveItem(WHITE_VIOLA, 30)
    sm.warp(SLEEPYWOOD)