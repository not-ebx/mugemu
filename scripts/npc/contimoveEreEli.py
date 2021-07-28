# Kiriru (1100003) | Sky Ferry (130000210)

map = 200090031

response = sm.sendAskYesNo("Would you like to go to Victoria Island?")

if response:
    sm.warp(map)
