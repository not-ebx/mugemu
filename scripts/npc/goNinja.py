# Palanquin (9110107) | Outside Ninja Castle (800040000)

mushroomShrine = 800000000
response = sm.sendAskYesNo("Would you like to go to #m" + str(mushroomShrine) + "m#?")
if response:
    sm.warp(mushroomShrine)