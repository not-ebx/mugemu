# Maple Adjustment Period (57458) | Kanna 2nd Job

haku = 9130081

cacophonous = 1142507

sm.removeEscapeButton()
sm.setSpeakerID(haku)
sm.setBoxChat()
sm.sendNext("Finally, your real skills are coming back! I'm tired of doing all the work!")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("What exactly do you do, other than sleep?")

sm.setSpeakerID(haku)
sm.setBoxChat()
sm.sendNext("I do all kinds of stuff... when I have enough Mana, "
"which, might I add, I am still waiting for!")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("That reminds me. I should release some of the Mana I've stored up. "
"The weak magic I've been using won't get me very far.")
sm.sendNext("Time to buff up my magic. I'll be stronger in no time!")

sm.setSpeakerID(haku)
sm.setBoxChat()
sm.sendNext("Hey! Are you trying to starve me to death? "
"Without Mana, I might as well be a house cat!")

sm.flipBoxChat()
sm.flipBoxChatPlayerAsSpeaker()
sm.sendNext("Relax, furball. We have to be careful about how we use Mana in this new world. "
"There's no telling what it could do.")
sm.sendNext("(There's no way this will be enough to overthrow Nobunaga and rescue the princess. "
"I'll have to train to become more powerful.)")

if sm.canHold(cacophonous):
    sm.jobAdvance(4210)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)
    sm.giveItem(cacophonous)
else:
    sm.sendNext("Please make space in your Equip inventory.")
