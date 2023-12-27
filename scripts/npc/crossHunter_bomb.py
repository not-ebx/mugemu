from net.mugeemu.ms.enums import WeatherEffNoticeType

sm.createQuestWithQRValue(1641, "bomb")
sm.showWeatherNotice("Watch out!", WeatherEffNoticeType.SilentCrusade)
sm.showEffect("Map/Effect.img/crossHunter/bomb")