# Mazra (2100006) | Ariant (260000000)
# 39000 - 39990

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
hairColour = al.getHair() % 10
baseID = 39000

for i in range(0, 1000, 10):
    hair = baseID + i + hairColour
    if not StringData.getItemStringById(hair) is None:
        options.append(hair)

answer = sm.sendAskAvatar("Choose your new hairstyle!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
