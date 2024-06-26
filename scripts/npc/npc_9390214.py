# Madame Song (9390214) | San Commerci (865000000)

from net.mugeemu.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
faceColour = al.getFace() % 1000 - al.getFace() % 100
baseFace = al.getFace() - faceColour

for colour in range(0, 900, 100):
    colourOption = baseFace + colour
    if not StringData.getItemStringById(colourOption) is None:
        options.append(colourOption)

answer = sm.sendAskAvatar("With our specialized machine, you can see the results of your potential treatment in advance. "
"What kind of lens would you like to wear? Please choose the style of your liking.", False, False, options)
if answer < len(options):
    sm.changeCharacterLook(options[answer])