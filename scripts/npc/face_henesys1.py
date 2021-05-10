# Denma the Owner (1052004) | Henesys Plastic Surgery (100000103)
# Male: 20000 - 20099 (Motivated to Tess)
# Female: 21000 - 21099 (Defiant to Futuroid)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
faceColour = al.getFace() % 1000 - al.getFace() % 100

if al.getGender() == 0:
    baseID = 20000
else:
    baseID = 21000

for i in range(100):
    face = baseID + faceColour + i
    if not StringData.getItemStringById(face) is None:
        options.append(face)

answer = sm.sendAskAvatar("Choose your new face!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
