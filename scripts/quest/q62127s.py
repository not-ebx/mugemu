# Watcha looking at?! (62127)
# TO DO: Literally all of the cutscene directing upon clicking on the quest

huiKoonKit = 9310537

sm.removeEscapeButton()
sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("...")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("...?")

sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("...")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Excuse me, sir. Why do you keep staring at me?")

sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("Excuse ME, pal. Why do YOU keep staring at ME?")

sm.localEmotion(5, 2000, False)
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("No way. YOU stared first!")

sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("Are you calling me a liar, pal?!")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Ugh. Not worth it. Goodbye.")

sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("Ahem. Actually. Now that we're talking... \r\n"
"Where'd you get those clothes? No, doesn't matter. Will you sell them to me?")

sm.localEmotion(3, 2000, False)
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("What? You want to buy my clothes? That's so weird. Um, they're pretty expensive...")

sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("Just name a price. I'm good for it.")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Are you sure? Okay, then...")

# sm.forcedMove(False, 75)
sm.sendNext("Ten thousand mesos apiece?")

# sm.forcedMove(True, 75)
# sm.forcedFlip(False)
# ! bubble effect appears over Hui Koon Kit right after

sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("...")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("...")

sm.setSpeakerID(huiKoonKit)
sm.setBoxChat()
sm.sendNext("You know, now that I've gotten a closer look, the quality isn't there. I'd know, you see. "
"The name's Hui Koon Kit, and I'm an antiques merchant. Used to be the best one in all of Dong Tai Road.")
sm.sendNext("You find any rare-looking items while you're out there, you bring them to ME. "
"I'll appraise them accurately, unlike the other goons around here, and give you the fairest price.")

sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.giveExp(170598)