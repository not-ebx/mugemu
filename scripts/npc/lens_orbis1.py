# Dr. Rhomes (9200101) | Orbis Plastic Surgery (200000201)

options = []

al = chr.getAvatarData().getAvatarLook()
faceColour = al.getFace() % 1000 - al.getFace() % 100
baseFace = al.getFace() - faceColour

for colour in range(0, 900, 100):
    colourOption = baseFace + colour
    options.append(colourOption)

answer = sm.sendAskAvatar("With our specialized machine, you can see the results of your potential treatment in advance."
"What kind of lens would you like to wear? Please choose the style of your liking.", False, False, options)
if answer < len(options):
    sm.changeCharacterLook(options[answer])
