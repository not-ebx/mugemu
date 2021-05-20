# Monarch's Power (24011)

greatSpirit = 1033210

sm.setSpeakerID(greatSpirit)
sm.sendNext("...What happened to you, deary? I can't feel the Royal Power on you at all. "
"All I'm getting is this nasty dark curse-y energy...")

sm.setPlayerAsSpeaker()
sm.sendSay("It's a long story.")

sm.setSpeakerID(greatSpirit)
sm.sendSay("I love me a long story! Remember, I'm a spirit, deary. "
"I don't have much to do but float around and listen to stories. So come on, tell us!")

sm.setPlayerAsSpeaker()
sm.sendSay("(You told her about the battle against the Black Mage, the curse, "
"and the present-day Maple World.)")
sm.sendSay("Maybe it's my fault. Maybe I should have stayed out of the fight against the Black Mage, "
"and spared my people this suffering. Great Spirit, if you feel I should step down as ruler, I will.")

sm.setSpeakerID(greatSpirit)
sm.sendSay("Oh dear. Don't worry your little head. You're doing the best you can by the Elves. "
"It's a right pity there's so much evil in the Maple World.")
sm.sendSay("You don't think this mean old Black Mage would've left the Elves alone, do you? "
"After he were done with the humans, you'd've been next, anyway. "
"You were right to fight him the first time, and you're right to fight him this time...")
response = sm.sendAskYesNo("Don't you waste another moment second guessing yourself, deary. "
"I'm not even gonna make you take the royal test again, neither. The Royal Power is all yours.")
if response:
    sm.startQuest(parentID)