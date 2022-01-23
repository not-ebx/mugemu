# Lost Memories Found [Kanna] (57465)

recoveredMemory = 7081

mouriMotonari = 9130008

sm.setSpeakerID(mouriMotonari)
sm.sendNext("Kanna, your strength returns. I am nothing short of amazed at your fervor. "
"Both Japan and Maple World shall remember your name.")
sm.sendSay("Kanna, I'm thankful for your dedication. Your loyalty was... unexpected.")
sm.sendSay("I'm glad you're on my side, Kanna.")

sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.startQuest(recoveredMemory)
sm.setQRValue(recoveredMemory, "1", False)