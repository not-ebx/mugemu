# Franz the Owner (2010002) | Orbis Plastic Surgery (200000201)
# Male: 23000 - 23099 (Vibrant Explorer to He Who Awakened)
# Female: 24000 - 24099 (Sugar to Alicia)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
faceColour = al.getFace() % 1000 - al.getFace() % 100

if al.getGender() == 0:
    baseID = 23000
else:
    baseID = 24000

for i in range(100):
    face = baseID + faceColour + i
    if not StringData.getItemStringById(face) is None:
        options.append(face)

answer = sm.sendAskAvatar("Choose your new face!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
