# Knight Stronghold: Secret Grove
# Quest: Rescue Neinhart
from net.swordie.ms.enums import WeatherEffNoticeType

KNIGHT_DISTRICT_4 = 271030400
WATCHMAN = 8610016
ENEMY_SPAWNS = [(635, 208), (159, 208), (59, 208), (-313, 208)]

sm.showWeatherNotice("Defeat all the monsters surrounding Neinheart to rescue him!", WeatherEffNoticeType.SnowySnowAndSprinkledFlowerAndSoapBubbles, 10000)
sm.setInstanceTime(600, KNIGHT_DISTRICT_4, 3)

for coords in ENEMY_SPAWNS:
    for z in range(3):
        sm.spawnMob(WATCHMAN, coords[0] + z, coords[1], False) # we add z so they dont spawn all clumped together