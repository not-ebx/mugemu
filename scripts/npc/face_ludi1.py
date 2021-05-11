# Ellie (2041010) | Ludibrium Plastic Surgery (220000003)
# Male: 25000 - 25099 (Crimsonheart to Hysteric)
# Female: 26000 - 26099 (Glona to Hysteric)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
faceColour = al.getFace() % 1000 - al.getFace() % 100

if al.getGender() == 0:
    baseID = 25000
else:
    baseID = 26000

for i in range(100):
    face = baseID + faceColour + i
    if not StringData.getItemStringById(face) is None:
        options.append(face)

answer = sm.sendAskAvatar("Choose your new face!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
