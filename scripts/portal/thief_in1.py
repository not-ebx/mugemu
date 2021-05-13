# Rocky Hill (260010401) => Red Scorpion's Lair

voice = 2103008
redScorpion = 260010402

sm.setSpeakerID(voice)
answer = sm.sendAskText("If you want to open the door, then yell out the magic word...", "", 1, 15)

if answer.lower() == "open sesame":
    sm.warp(260010402)
else:
    sm.sendSayOkay("#b (The door remains closed.)")