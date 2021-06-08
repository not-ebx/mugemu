# Powerless [Hayato] (57105)

mouri = 9130008

sm.setBoxChat()
sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Argh! I-I cannot control my muscles. I feel as though I were covered in rice paste. "
"Are my injuries that severe?")

sm.setSpeakerID(mouri)
sm.setBoxChat()
sm.sendNext("The rules of this world are quite different from Japan. "
"The shift in spiritual energies has weakened even the strongest among us.")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("What good is all my training if I cannot move without hindrance? "
"What curse has befallen us?")

sm.setSpeakerID(mouri)
sm.setBoxChat()
response = sm.sendAskYesNo("I have no need for soldiers without the simple willpower to overcome a small obstacle. "
"There is a solution to every problem.")
if response:
    sm.completeQuest(parentID)
