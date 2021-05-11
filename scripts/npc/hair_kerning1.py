# Don Giovanni (1052100) | Kerning City Hair Salon (103000005)
# Male: 33000 - 33990 (Prince Cut to Hot Top)
# Female: 34000 - 34990 (Palm Tree to Jett)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
hairColour = al.getHair() % 10

if al.getGender() == 0:
    baseID = 33000
else:
    baseID = 34000

for i in range(0, 1000, 10):
    hair = baseID + i + hairColour
    if not StringData.getItemStringById(hair) is None:
        options.append(hair)

answer = sm.sendAskAvatar("Choose your new hairstyle!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
