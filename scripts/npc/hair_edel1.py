# Fabio (2150003) | Edelstein Hair Salon (310000003)
# Male: 40000 - 40990 (Boomy to Bandana)
# Female: 41000 - 41990 (Free to Bandana)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()
hairColour = al.getHair() % 10

# Edelstein's Hair Salon has just Fabio in it
# For convenience's sake, he can also change hair colour
selection = sm.sendNext("Hello. How can I help you?"
            "\r\n#b"
            "#L0#Change hairstyle#l\r\n"
            "#L1#Change hair colour#l")

if selection == 0:
    if al.getGender() == 0:
        baseID = 40000
    else:
        baseID = 41000

    for i in range(0, 1000, 10):
        hair = baseID + i + hairColour
        if not StringData.getItemStringById(hair) is None:
            options.append(hair)

    answer = sm.sendAskAvatar("Choose your new hairstyle!", False, False, options)

    if answer < len(options):
        sm.changeCharacterLook(options[answer])

else:
    baseHair = al.getHair() - hairColour

    for colour in range(8):
        colourOption = baseHair + colour
        options.append(colourOption)

    answer = sm.sendAskAvatar("Choose your new hair colour!", False, False, options)
    if answer < len(options):
        sm.changeCharacterLook(options[answer])