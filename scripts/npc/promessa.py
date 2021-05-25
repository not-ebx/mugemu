# Promessa (2300005) | Veritas (230050000)

aquarium = 230000000
response = sm.sendAskYesNo("Would you like to go to #m" + str(aquarium) + "m#?")
if response:
    sm.warp(aquarium)