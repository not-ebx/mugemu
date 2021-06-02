# [Job Advancement] Getting Caught?! (23615) | Xenon 3rd Job

stephan = 2159421

caught = 931060030

sm.setSpeakerID(stephan)
sm.sendNext("The Watchman are all over. I need to get out of here!")

sm.setPlayerAsSpeaker()
response = sm.sendAskYesNo("The Watchman is headed this way, but I can't arouse any suspicions. "
"Maybe I should just try to act casual...")
if response:
    sm.sendNext("No, I won't run. I can do this. I just need to be a Black Wing. "
    "I am a Black Wing, I am a Black Wing, I am a...")
    sm.startQuest(parentID)
    sm.warpInstanceIn(caught)