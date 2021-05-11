redScorpion = 260010402
answer = sm.sendAskText("If you want to open the door, then yell out the magic word...", "", 1, 15)

if answer.lower() == "open sesame":
    sm.warp(260010402)
else:
    sm.sendSayOkay("#b (The door remains closed.)")