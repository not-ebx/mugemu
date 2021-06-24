# Powerless [Kanna] (57403)

mouri = 9130008

sm.removeEscapeButton()
sm.setSpeakerID(mouri)
sm.setBoxChat()
sm.sendNext("Ah, you will...")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("I'm Kanna, Tsuchimikado Haruaki's star pupil. "
"I believe we met briefly during the Honnou-ji raid, but I'd like to formally introduce myself.")

sm.setSpeakerID(mouri)
sm.setBoxChat()
sm.sendNext("You dare invite yourself into Momijigaoka? This is a sacred place, a slice of Japan. "
"My sons and I didn't toil for days to build fortifications and clear enemies "
"just to have a blundering fool treat the place with disrespect.")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Excuse me? Kanetsugu sent me here, but obviously I have the wrong person.")

sm.setSpeakerID(mouri)
sm.setBoxChat()
response = sm.sendAskYesNo("You are the one Kanetsugu spoke of? No, no. Don't leave. "
"I may need your help. First, show me you can still move normally. "
"The workings of this world are... different.")
if response:
    sm.startQuest(parentID)
