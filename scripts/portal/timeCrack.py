# Crack in Time (272000000) => Leafre in Flames or Distorted Temple of Time

optionList = [272000100, 272020000]
statue = 2144018

destString = ["Where would you like to go?\r\n"]
for index, option in enumerate(optionList):
    destString.append(''.join(["#L", repr(index), "##m", repr(option), "##l\r\n"]))

sm.setSpeakerID(statue)
destIndex = sm.sendNext(''.join(destString))
sm.warp(optionList[destIndex])