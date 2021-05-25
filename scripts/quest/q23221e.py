# Calm Before the Storm (23221) | DA 4th Job

rageMaster = 1142556

MASTEMA = 2151009

sm.setSpeakerID(MASTEMA)
sm.sendNext("You made it back, #h #! How are you?")

sm.flipDialoguePlayerAsSpeaker()
sm.sendSay("I didn't know I had such anger within me. It is not easy to control.")

sm.setSpeakerID(MASTEMA)
if sm.sendAskYesNo("But you succeeded, #h #! I should write this down for posterity, right?"):
    if sm.canHold(rageMaster):
        sm.completeQuest(parentID)
        sm.giveItem(rageMaster)
        sm.giveAndEquip(1099009)
        sm.jobAdvance(chr.getJob()+1)
        sm.sendSayOkay("Your inner rage is now under your control, #h #! All that's left for you is to keep training.")
    else:
        sm.sendSayOkay("Please make room in your Equip inventory.")