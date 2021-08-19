# Sleepy Expression (6506)

calico = 1092004

drool = 5160031

sm.setSpeakerID(calico)
sm.sendNext("Zzzzz...")

sm.setPlayerAsSpeaker()
sm.sendSay("#b(#p" + str(calico) + "# is fast asleep. Observe his expression. "
"His eyes are closed and his mouth is slightly open... "
"Adding some drool would make the picture even more realistic...)")
sm.sendSay("#b(Ew, a bubble just formed from #p" + str(calico) + "#'s nose! "
"Hmm...that actually gave it an even more realistic look. "
"Try making an expression that even exceeds the look on #p" + str(calico) + "#'s face. \r\n\r\n"
"#fUI/UIWindow2.img/QuestIcon/4/0# \r\n"
"#i" + str(drool) + "# #t" + str(drool) + "# x 1")

sm.giveItem(drool)
sm.completeQuest(parentID)

sm.sendNext("#b(You learned the Sleeping Expression from Calico. "
"By studying him, you made an even more convincing Sleepy Expression.)")