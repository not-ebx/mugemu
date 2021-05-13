# Irene (9270041)

changiAirport = 540010000
response = sm.sendAskYesNo("Would you like to go to Singapore?")
if response:
    sm.warp(changiAirport)