# Natalie (1012103) | Henesys Hair Salon (100000104)
# Male: 30000 - 30990 (Toben to Tentacle)
# Female: 31000 - 31990 (Sammy to Evan)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
hairColour = al.getHair() % 10

if al.getGender() == 0:
    baseID = 30000
else:
    baseID = 31000

for i in range(0, 1000, 10):
    hair = baseID + i + hairColour
    if not StringData.getItemStringById(hair) is None:
        options.append(hair)

answer = sm.sendAskAvatar("Choose your new hairstyle!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
