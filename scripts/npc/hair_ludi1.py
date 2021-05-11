# Miyu (2041007) | Ludibrium Hair Salon (220000004)
# Male: 36000 - 36990 (Eastern Rocker to Big Point)
# Female: 38000 - 38990 (Harmony to Glam Shiny)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
hairColour = al.getHair() % 10

if al.getGender() == 0:
    baseID = 36000
else:
    baseID = 38000

for i in range(0, 1000, 10):
    hair = baseID + i + hairColour
    if not StringData.getItemStringById(hair) is None:
        options.append(hair)

answer = sm.sendAskAvatar("Choose your new hairstyle!", False, False, options)

if answer < len(options):
    sm.changeCharacterLook(options[answer])
