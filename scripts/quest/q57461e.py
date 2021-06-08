# Awakened Spirit (57461)

spiritShroom = 4033278
hakuMount = 80001157

haku = 9130081

sm.removeEscapeButton()
sm.setSpeakerID(haku)
sm.setBoxChat()
sm.sendNext("I feel great! The spiritual energy in the mushroom beefed up my reserves.")
sm.sendNext("(Munch, munch, munch.)")
sm.sendNext("Carrying you should be a piece of cake now. "
"Let me know when you need a ride. I'll just be chomping on more of this delicious mushroom.")

sm.completeQuest(parentID)
sm.giveSkill(hakuMount)
sm.consumeItem(spiritShroom)