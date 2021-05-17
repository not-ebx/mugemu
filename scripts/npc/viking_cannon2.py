# Cannon (1302009) | Gun Deck (106030701)

castleWalls = 106030300

response = sm.sendAskYesNo("Would you like to use the cannon to return to #m" + str(castleWalls) + "#?")
if response:
    sm.warp(castleWalls)