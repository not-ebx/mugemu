# Fox Tree (940200070) | Shade's Spirit Bond Max upgrade
# Used by [Skill] Missing Each Other (1542)

missing = 1542

spiritBondMaxNew = 25121133
echo = 20051005

moonbeam = 3002107
silver = 3002108
# Using tutorial Shade's NPC as a placeholder
# TO DO: Replace with deepcopy of player and remote spawn
gender = chr.getAvatarData().getAvatarLook().getGender()
if gender == 0:
    you = 2159449
else:
    you = 2159450

returnField = sm.getReturnField()
portal = sm.getReturnPortal()

sm.spawnNpc(moonbeam, 187, -134)
sm.spawnNpc(you, 242, -134)
sm.spawnNpc(silver, 80, -133)
# These two don't show up until later
sm.hideNpcByTemplateId(silver, True)
sm.hideNpcByTemplateId(you, True)

sm.lockInGameUI(True)
sm.removeEscapeButton()
sm.setPlayerAsSpeaker()
sm.sendNext("Moonbeam.")

sm.setSpeakerID(moonbeam)
sm.sendSay("Who...? Ah!")

sm.setPlayerAsSpeaker()
sm.sendSay("It's been a while... "
"You've probably forgotten all about me, but I came to return something.")

sm.setSpeakerID(moonbeam)
sm.sendSay("Whoa, cool! You look exactly like my guardian spirit! "
"You don't have ears or a tail, and the shape of your eyes is identical!")

sm.setPlayerAsSpeaker()
sm.sendSay("What do you mean? You lent your guardian spirit to me...")

sm.setSpeakerID(moonbeam)
sm.sendSay("Hey, hey! You wanna see my guardian spirit?")

sm.showEffect("Effect/Direction19.img/effect/eunwol/0", 200, 242, 10)
sm.sendDelay(200)
sm.hideNpcByTemplateId(you, False)
sm.sendDelay(500)

# TO DO: Find the balloon message effect with just 3 !s, not 4
sm.showBalloonMsg("Effect/Direction12.img/effect/tuto/BalloonMsg1/2", 1000)
sm.sendDelay(1000)

sm.setPlayerAsSpeaker()
sm.sendNext("(It's real. Moonbeam got a guardian spirit, and it looks just like you! "
"How could this be?)")

sm.setSpeakerID(moonbeam)
sm.sendNext("I also received a guardian spirit after praying to the fox god all day and all night! "
"It's way cooler than everyone else's! Now I don't feel so lonely anymore!")
sm.sendSay("Hey, where'd you come from? Why do you look so much like my guardian spirit? Hmm? Hmm?")

sm.hideNpcByTemplateId(silver, False)
sm.setSpeakerID(silver)
sm.sendSay("What's the matter, Moonbeam?")

sm.setSpeakerID(moonbeam)
sm.sendSay("Grandpa!")

sm.setSpeakerID(silver)
sm.sendSay("Phew! I came to see what all the noise was about, and lookie here! "
"A suspicious outsider! Tell me, what business do you have with Moonbeam?")

sm.setSpeakerID(moonbeam)
sm.sendSay("Grandpa! Don't be so mean to someone who looks just like my guardian spirit!")

sm.flipNpcByTemplateId(silver, False)
sm.sendDelay(1000)
sm.flipNpcByTemplateId(silver, True)
sm.sendDelay(500)

sm.setSpeakerID(silver)
sm.sendNext("Oh, my! You're right! Would you care to explain why this is happening?")

sm.setPlayerAsSpeaker()
sm.sendSay("I'm curious myself. How could this be?")

sm.setSpeakerID(silver)
sm.sendSay("I've never seen anything like this before...")
sm.sendSay("Could it be...? I thought it was only a legend...")
sm.sendSay("It's said that the guardian spirit of a fox grows to take the shape of those they long for. "
"They're said to be more powerful than any other spirit.")

sm.setSpeakerID(moonbeam)
sm.sendSay("No way, Grandpa! How could I long for someone I've never even met?")

sm.setSpeakerID(silver)
sm.sendSay("That's what I'm curious about myself, Moonbeam. "
"You say you've never seen this ruffian before?")

sm.setSpeakerID(moonbeam)
sm.sendSay("No! This is the first time! Why did you come here, anyway?")

sm.setPlayerAsSpeaker()
sm.sendSay("I borrowed a guardian spirit from you, and I'm here to return it.")

sm.setSpeakerID(moonbeam)
sm.sendSay("Huh? What do you mean? What guardian spirit? "
"I already have an awesome guardian spirit! Thanks, but I don't need another one.")

sm.setPlayerAsSpeaker()
sm.sendSay("...All right. (It hurts to hear this, but you'll be okay. "
"It might start to hurt more if you stick around here, though.)")

sm.forcedInput(1)
sm.sendDelay(300)
sm.forcedInput(0)

sm.setSpeakerID(moonbeam)
sm.sendNext("Wait! Are you leaving already? Will you at least tell me your name?")

sm.forcedInput(2)
sm.sendDelay(100)
sm.forcedInput(0)

sm.setPlayerAsSpeaker()
sm.sendNext("...You're the one who game me my name, Moonbeam. "
"You'll remember it someday.")

sm.setSpeakerID(moonbeam)
sm.sendSay("Whaaa... No, that's not fair!")

sm.setPlayerAsSpeaker()
sm.sendSay("Because of you, I have a reason to keep going.")
sm.sendSay("You mean so much to me, and you one felt the same way... "
"I promise, you'll remember who I am. I promise...")
sm.sendSay("I'll come back one I figure out how to make that happen. "
"Will you wait for me?")

sm.setSpeakerID(moonbeam)
sm.sendSay("Sure! I have no idea what you're talking about, but come back any time!")

sm.setPlayerAsSpeaker()
sm.sendSay("Thanks... (You wonder if your guardian spirit will take Moonbeam's form since you miss her.)")

# Currently, the old SBM won't hide from your hyper skills even if removed
# Removing the old SBM would also break the upgraded SBM
sm.giveSkill(spiritBondMaxNew)
sm.giveSkill(echo)
sm.chatScript("You can now materialize Moonbeam by using Spirit Bond Max.")

sm.forcedInput(1)
sm.sendDelay(1000)

sm.startQuest(missing)
sm.completeQuest(missing)
sm.warp(returnField, 0)
sm.lockInGameUI(False)
