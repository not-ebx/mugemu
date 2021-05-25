# Kerkaporta (2230212) | Ranheim Academy (241000110)

leafre = 240000000

response = sm.sendAskYesNo("Would you like to return to Leafre?")

if response:
    sm.warp(leafre)