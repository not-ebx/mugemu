# Gate to the Future: Gate to the Future
# Quest: Into The Light

INTO_THE_LIGHT = 31102
SHINSOO_SPEAKER = 9400029

if sm.hasQuest(INTO_THE_LIGHT):
    sm.removeEscapeButton()
    sm.setSpeakerID(SHINSOO_SPEAKER)
    sm.flipDialogue()
    sm.sendNext(".........!!!!!!!!!")
    
    sm.setPlayerAsSpeaker()
    sm.sendSay("Shinsoo? Why? It seemed like he was trying to say"
                "\r\nsomething... I better head deeper into the future. What"
                "\r\ncould Shinsoo have been trying to say?")
    sm.completeQuest(INTO_THE_LIGHT)

sm.dispose()