# Road to the Mine 1 (931060030) | Xenon 3rd Job

lackey = 2159397
gelimer = 2154009

goon = 9300643

sm.lockInGameUI(True)
sm.spawnNpc(lackey, 648, 28)

# TO DO: Figure out why the lackey doesn't move and just spazes in place (initial start x: 1188)
# sm.moveCamera(100, 738, ground)
# sm.sendDelay(1000)
# sm.moveCameraBack(100)

# sm.moveNpcByTemplateId(lackey, True, 540, 60)
# sm.sendDelay(4000)

sm.removeEscapeButton()
sm.setSpeakerID(lackey)
sm.sendNext("Hey, what're you doing out here? And where did that other guy go? "
"You don't look familiar...")

sm.setPlayerAsSpeaker()
sm.sendSay("I am a Black Wing.")

sm.setSpeakerID(lackey)
sm.sendSay("Are you now? Let me see here... I think I've seen your face before. "
"I think I saw you in some orders I got from #p" + str(gelimer) + "#.")

sm.setPlayerAsSpeaker()
sm.sendSay("You are mistaken.")

sm.setSpeakerID(lackey)
sm.sendSay("I am? Maybe I'd better check with #p" + str(gelimer) + "#. "
"I don't want to get into hot water. Come along!")

sm.setPlayerAsSpeaker()
sm.sendSay("Maybe I should have just taken this guy out...")

sm.removeNpc(lackey)
sm.spawnMob(goon, 648, 28, False)
sm.chatScript("Defeat the Black Wings.")
sm.lockInGameUI(False)