# Kiriru (1100007) | Station to Ereve (104020120)

map = 200090030

response = sm.sendAskYesNo("Would you like to go to Ereve?")

if response:
    sm.warp(map)
