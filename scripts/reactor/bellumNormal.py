from net.mugeemu.ms.enums import WeatherEffNoticeType

sm.removeReactor()
sm.invokeAfterDelay(1500, "spawnMob", 9400942, -200, 440, False)
sm.showWeatherNotice("You ignore my warnings?! I will show you no mercy!", WeatherEffNoticeType.BossVellum, 10000)
sm.dispose()
