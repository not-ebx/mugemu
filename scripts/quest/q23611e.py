# [Job Advancement] Veritas's Finest (23611) | Xenon 2nd Job

promessa = 30021235
secretAgent = 1142576

dreamboat = 2300001
veritas = 230050000
claudine = 2151003

sm.setSpeakerID(dreamboat)
sm.sendNext("Welcome. You must be the one Claudine mentioned. I am Professor Dreamboat... Moan. "
"Sorry, the nickname's sort of hard to escape. "
"I am the head of Resistance Research Command, otherwise known as #b#m" + str(veritas) + "##k.")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("#b#m" + str(veritas) + "##k?")

sm.setSpeakerID(dreamboat)
sm.sendSay("I doubt #p" + str(claudine) + "# gave you the details over an insecure channel. "
"We are a research group, dedicated to tracking and addressing strange phenomena across Maple World "
"that may be related to the Black Mage. "
"We have gathered scholars of all areas of expertise to investigate these happenings.")
sm.sendSay("Unfortunately, we are sorely lacking in people. "
"That's why we're so glad to have a field agent from the Resistance with us.")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("But, I... I'm not free to go where I please right now.")

sm.setSpeakerID(dreamboat)
sm.sendSay("Ah, yes, someone is tracking you, right? Well, no worries. "
"I'm sure somebody here can figure that part out. "
"This base is completely undetectable to any scanners, so you're safe enough inside.")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("Interesting. Would it be possible to create a wearable version of your scrambling systems? "
"Prehaps that would...")

sm.setSpeakerID(dreamboat)
sm.sendSay("Slow down there, buddy. You're not a building. "
"We can't just load you down with three-foot-thick lead... Or can we?")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("I can carry a great deal of weight with my current booster alignment.")

sm.setSpeakerID(dreamboat)
sm.sendSay("You'd be the size of a two story building. It's not a good plan.")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("Unfortunate... I suppose that means this is the only place I can feel safe.")

sm.setSpeakerID(dreamboat)
sm.sendSay("Well, there might be another way. Maybe we can create a #bPulse Disruptor#k.")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("#bPulse Disruptor#k?")

sm.setSpeakerID(dreamboat)
sm.sendSay("If you're giving off a signal, I'm sure we can create SOME sort of counter-system to block it out. "
"It would require multiple devices though, probably scattered all over...")
sm.sendSay("This is actually very similar to something #p" + str(claudine) + "# requested. "
"I bet I can dig up that research somewhere...")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("Is there anything I can do?")

sm.setSpeakerID(dreamboat)
sm.sendSay("Well, it's probably a little below your abilities, "
"but all the scientists around here could use an assistant, myself included.")

sm.setSpeakerID(dreamboat)
response = sm.sendAskYesNo("If you're willing to take the job, "
"I'm ready to hire you as the one-and-only #b#m" + str(veritas) + "##k special agent. Do you accept? #b#e \r\n\r\n"
"<Accept 2nd Job Advancement.>")
if response:
    if sm.canHold(secretAgent):
        sm.jobAdvance(3610)
        sm.completeQuest(parentID)
        sm.giveSkill(promessa)
        sm.giveItem(secretAgent)
        sm.sendNext("Congratulations, special agent #h #!")
        sm.sendSay("You probably saw it when you were walking in, "
        "but one of your engineers created the world's gaudiest transport device. "
        "You can use it to go out on missions, or get back to the lab any time.")
        sm.sendSay("He named it the #bPromessa#k."
        "You can use the #b#q" + str(promessa) + "##k skill to call it wherever you are.")
        sm.sendPrev("I know it's pretty ridiculous-looking, but the design is solid as can be.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")
else:
    sm.sendSayOkay("I'll give you some time to think about the offer.")