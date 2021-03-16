# Forest of Tenacity: Stage 4
# Quest: John's Present

SLEEPYWOOD = 105000000
BLUE_FLOWER = 1063001
BLUE_VIOLA = 4031026 # quest item
JOHNS_PRESENT = 2053 # quest
MIN_DIST = 275

sm.setSpeakerID(BLUE_FLOWER)
if abs(sm.getObjectPositionY() - (sm.getChr().getPosition().getY())) > MIN_DIST: # we check for distance between player and flower
    sm.sendSayOkay("You can't see the inside of the pile of flowers very well because"
                    "\r\nyou're too far. Go a little closer.")
else:
    if sm.hasQuest(JOHNS_PRESENT):
        if sm.canHold(BLUE_VIOLA):
            sm.giveItem(BLUE_VIOLA, 20)
        else:
            sm.sendSayOkay("Please make more space in your ETC inventory.")
            sm.dispose()
    sm.warp(SLEEPYWOOD)