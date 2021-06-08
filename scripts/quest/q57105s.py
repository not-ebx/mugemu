# Powerless [Hayato] (57105)

mouri = 9130008

sm.setSpeakerID(mouri)
sm.setBoxChat()
sm.sendNext("Who are you?")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Mouri Motonari! We met at the Honnou-ji military council. "
"I am Anegasaki Kenji, a retainer to the Matsuyama clan. I've just arrived.")

sm.setSpeakerID(mouri)
sm.setBoxChat()
sm.sendNext("The Mouri welcome you to Momijigaoka, Matsuyama clan. "
"My sons and I were the first to arrive, so we set up a base of operations. "
"We must all work together, under my guidance, to adjust to this new world.")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("What do you mean 'adjust'?")

sm.setSpeakerID(mouri)
sm.setBoxChat()
response = sm.sendAskYesNo("Attempt to move as you normally would, Anegasaki Kenji.")
if response:
    sm.startQuest(parentID)
