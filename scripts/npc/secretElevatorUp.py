# Elevator Control (2154015) | Android Research Lab 3 (310060120)

edelSide = 310030200

response = sm.sendAskYesNo("Would you like to use the elevator to go up to Edelstein?")
if response:
    sm.warp(edelSide, 3)