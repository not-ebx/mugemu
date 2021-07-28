# Where You Came From [Kinesis] (3563)

memoryKeeper = 3507

gender = chr.getAvatarData().getAvatarLook().getGender()
if gender == 0:
    kinesis = 1531000
else:
    kinesis = 1531052
jay = 1531001

sm.setSpeakerID(kinesis)
sm.setBoxChat()
sm.sendNext("Jay, can you hear me?")

sm.setSpeakerID(jay)
sm.setBoxChat()
sm.sendNext("#face9#I can hear you. What's up?")

sm.setSpeakerID(kinesis)
sm.setBoxChat()
sm.sendNext("Do you have any good memories of the two of us?")

sm.setSpeakerID(jay)
sm.setBoxChat()
sm.sendNext("#face10#What? Who are you? \r\n"
"What did you do to Kinesis?")

sm.setBoxChat()
sm.sendNext("#face10#Kinesis never asks such embarassing questions!")

sm.setSpeakerID(kinesis)
sm.setBoxChat()
sm.sendNext("#face2#......")

sm.setSpeakerID(jay)
sm.setBoxChat()
sm.sendNext("#face11#Hey, I'm just kidding! \r\n"
"Hmm...Our memories... \r\n"
"I only remember that you exploited me.")

sm.setSpeakerID(kinesis)
sm.setBoxChat()
sm.sendNext("#face2#That's it? \r\n"
"Come on! Think further back.")

sm.setSpeakerID(jay)
sm.setBoxChat()
sm.sendNext("#face11#Well, there's things like how you used to tease me, "
"and threw dirty stuff at me when were kids because you knew I really hated that...")

sm.setSpeakerID(jay)
sm.setBoxChat()
sm.sendNext("#face12#Oh, how about this? Remember how you stole the girl who confessed her love to me? "
"You told me you would make sure what she said was true and made her fall in love with you.")

sm.setSpeakerID(kinesis)
sm.setBoxChat()
sm.sendNext("#face2#Okay, I'll stop now. The talk is over. \r\n"
"#b(It was good to talk to an old friend after all these years.)")

sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.completeQuest(memoryKeeper)