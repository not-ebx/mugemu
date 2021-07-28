# Shalon (9270038) | Changi Airport (540010000)

options = [104020100, 200000100]

destination = sm.sendNext("Where would you like to go? #b\r\n"
"#L0# Victoria Island #l\r\n"
"#L1# Orbis #l\r\n")
sm.warp(options[destination])