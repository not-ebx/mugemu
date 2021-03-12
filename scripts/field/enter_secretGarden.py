# Knight Stronghold: Secret Grove
# Quest: Rescue Neinhart
from net.swordie.ms.enums import WeatherEffNoticeType

KNIGHT_DISTRICT_4 = 271030400
WATCHMAN = 8610016
ENEMY_SPAWNS = [{"x":635, "y":208}, {"x":159, "y":208}, {"x":59, "y":208}, {"x":-313, "y":208}]

sm.showWeatherNotice("Defeat all the monsters surrounding Neinheart to rescue him!", WeatherEffNoticeType.SnowySnowAndSprinkledFlowerAndSoapBubbles, 10000)
sm.setInstanceTime(600, KNIGHT_DISTRICT_4, 3)

for coords in ENEMY_SPAWNS:
    for z in range(3):
        sm.spawnMob(WATCHMAN, coords["x"] + z, coords["y"], False) # we add z so they dont spawn all clumped together