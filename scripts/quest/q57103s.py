# Picking up the Pieces (57103)

yukimori = 9130024

sm.setSpeakerID(yukimori)
sm.setBoxChat()
response = sm.sendAskYesNo("I am Yamanaka Yukimori, a retainer to the Amako clan. I was at Honnou-ji with you. Before we continue, may I ask your name?")
if response:
    sm.startQuest(parentID)

    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("My name is Anegasaki Kenji, heir to the Matsuyama clan, and the child of Anegasaki Tomonobu.")

    sm.setSpeakerID(yukimori)
    sm.setBoxChat()
    sm.sendNext("Anegasaki Kenji... I've heard that name before! It's my honor to meet the master of Battoujutsu.")

    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("Please do not fawn over me, noble warrior. If I had known you were at Honnou-ji, I would have greeted you as a brother.")

    sm.setSpeakerID(yukimori)
    sm.setBoxChat()
    sm.sendNext("Much has happened since the raid on Honnou-ji. I will fill you in when you have gathered yourself.")
