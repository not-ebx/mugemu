# Ace (2150008) | Edelstein Temporary Airport (310000010)

map = [200090710, 200090610]

answer = sm.sendSay("Where would you like to go? #b\r\n"
"#L0# Victoria Island #l\r\n"
"#L1# Orbis #l")
sm.warp(map[answer])