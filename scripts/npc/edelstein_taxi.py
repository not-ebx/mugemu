# Taxi (2150007) | Edelstein (1000064)

verneEntrance = 310040200
response = sm.sendAskYesNo("Would you like to go to #m" + str(verneEntrance) + "m#?")
if response:
    sm.warp(verneEntrance)