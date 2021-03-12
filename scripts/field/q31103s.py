# Henesys Ruins: Henesys Ruins
# Quest: Exploring The Future

EXPLORING_THE_FUTURE = 31103
INTO_THE_LIGHT = 31102
BIG_HEADWARD = 2142003

if sm.hasQuestCompleted(INTO_THE_LIGHT):
    if not sm.hasQuestCompleted(EXPLORING_THE_FUTURE) and not sm.hasQuest(EXPLORING_THE_FUTURE):
        sm.removeEscapeButton()

        sm.setSpeakerID(BIG_HEADWARD)
        sm.flipDialogue()
        sm.sendNext("Who are you? I've never seen you before. Where did you come from?")

        sm.setPlayerAsSpeaker()
        sm.sendSay("Aren't you...Big Headward? The hair stylist?!"
               "\r\n(The size of his head certainly hasn't changed...) I-I'm no one suspicious...")

        sm.setSpeakerID(BIG_HEADWARD)
        sm.flipDialogue()
        sm.sendSay("How'd you know I used to be a hair stylist? In any case,"
                    "\r\nlet's go see Chief Alex.")

        sm.setPlayerAsSpeaker()
        sm.sendSay("Chief Alex?"
                    "\r\n(It couldn't be that kid, could it?)")

        sm.setSpeakerID(BIG_HEADWARD)
        sm.flipDialogue()
        sm.sendSay("Hurry up! Move!!")

        sm.createQuestWithQRValue(EXPLORING_THE_FUTURE, "1") 
        sm.warp(271010000, 2) # Teleport to same map but portal to talk to Chief Alex
        
sm.dispose()