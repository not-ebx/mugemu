# Mino the Owner (2010001) | Orbis Hair Salon (200000202)
# Male: 35000 - 35990 (Bang-up to Rover)
# Female: 37000 - 37990 (Orchid to Apple Tea)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
hairColour = al.getHair() % 10

if al.getGender() == 0:
    baseID = 35000
else:
    baseID = 37000

for i in range(0, 1000, 10):
    hair = baseID + i + hairColour
    if not StringData.getItemStringById(hair) is None:
        options.append(hair)

answer = sm.sendAskAvatar("Choose your new hairstyle!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
