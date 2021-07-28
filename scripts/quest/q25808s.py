# Where You Came From [Kaiser] (25808)

memoryKeeper = 3507

sm.flipDialoguePlayerAsSpeaker()
sm.sendNext("Memories... Memories from when? When I became Kaiser?")
sm.sendSay("No, that's not good enough. Tear... Velderoth... "
"We were young and naive, but we believed. That was my beginning. "
"That was when I learned to fight for what I loved.")
sm.sendSay("Maybe Velderoth and I can be friends again one day, maybe not, "
"but I'll never forget the time I spent training with him.")
sm.sendSay("(Recalling your memories has given you a healthier perspective on life.)")

sm.startQuest(parentID)
sm.completeQuest(parentID)
sm.completeQuest(memoryKeeper)