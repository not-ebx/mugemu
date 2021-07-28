# Kiru (1100008) | Station <To Ereve> (200000161)

map = 200090020

response = sm.sendAskYesNo("Would you like to go to Ereve?")

if response:
    sm.warp(map)
