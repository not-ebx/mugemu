# [Job Advancement] Only the Brave (23612) | Xenon 3rd Job

promathus = 2300002
gelimer = 2154009
beryl = 2159417

bwHat = 1003134
mine = 310040000

sm.setSpeakerID(promathus)
sm.flipDialogue()
sm.sendNext("Ah, there you are. I must speak with you. "
"I don't know if you know this, but #p" + repr(gelimer) + "# has not ended his search for you.")

sm.flipDialogue()
sm.sendSay("The thing I find very strange is that #p" + repr(beryl) + "# was the only one to actually pursue you. "
"It seems your very existence was kept a secret, even among the Black Wings. "
"WAS... things tend to change rapidly in their organization.")

sm.flipDialogue()
sm.sendSay("Only a handful of the Black Wings will know of you now, "
"but it is only a matter of time until your infamy becomes a burden. "
"The people of this laboratory are busy at work for a weapon to make you safe... "
"but I am not so sure it is the best course of action. Do you know why?")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("Is it because I'm not human?")

sm.setSpeakerID(promathus)
sm.flipDialogue()
sm.sendNext("I would not be swayed by so petty a reason.")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("Is it because #p" + repr(gelimer) + "# is my creator?")

sm.setSpeakerID(promathus)
sm.flipDialogue()
sm.sendNext("Again, no. You were created as a weapon, and a weapon is only as dangerous as the person who wields it. "
"Who's to say that the evil dwelling within #p" + repr(gelimer) + "#'s soul has not infected mine as well? "
"All men are susceptible to greed, especially those fueled by the desire of knowledge.")

sm.flipDialogue()
response = sm.sendAskYesNo("A scientist must take responsibility for his own curiosity. "
"THAT is why I hesitate to grant you this power. \r\n"
"I need proof that you will handle it with care. Will you prove yourself to me?")
if response:
    sm.startQuest(parentID)
    sm.flipDialogue()
    sm.sendSayOkay("Splendid. I would like you to show me the courage in your heart, "
    "and bring me a #b#t" + repr(bwHat) + "##k. There is a gentleman on the #b#m" + repr(mine) + "##k. "
    "He is known to be rather unscrupulous towards his own organization. "
    "But be wary, their base is rather nearby. It will require skill, bravery, and cunning. Good luck.")
else:
    sm.flipDialogue()
    sm.sendSayOkay("I'm not giving you the weapon if you're unwilling to prove yourself.")