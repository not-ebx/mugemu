# Lagoon (2400004) | Zero's Temple (320000000)

from net.swordie.ms.loaders import StringData

options = []

al = chr.getAvatarData().getAvatarLook()

selection = sm.sendNext("Hello. How can I help you?"
            "\r\n#b"
            "#L0#Change hair colour#l\r\n"
            "#L1#Change eye colour#l\r\n"
            "#L2#Change skin tone#l")

if selection == 0:
    hairColour = al.getHair() % 10
    baseHair = al.getHair() - hairColour

    for colour in range(8):
        colourOption = baseHair + colour
        options.append(colourOption)

    answer = sm.sendAskAvatar("Choose your new hair colour!", False, False, options)
    if answer < len(options):
        sm.changeCharacterLook(options[answer])

elif selection == 1:
    faceColour = al.getFace() % 1000 - al.getFace() % 100
    baseFace = al.getFace() - faceColour

    for colour in range(0, 900, 100):
        colourOption = baseFace + colour
        options.append(colourOption)

    answer = sm.sendAskAvatar("With our specialized machine, you can see the results of your potential treatment in advance."
    "What kind of lens would you like to wear? Please choose the style of your liking.", False, False, options)
    if answer < len(options):
        sm.changeCharacterLook(options[answer])

else:
    #These values will crash the client when attempting to load them onto character
    nullSkins = [6, 7, 8]

    for skin in range(14):
        #Skip past null skin values
        if skin in nullSkins:
            continue
        options.append(skin)

    answer = sm.sendAskAvatar("We have the latest in beauty equipment."
    "With our technology, you can preview what your skin will look like in advance!"
    "Which treatment would you like?", False, False, options)
    if answer < len(options):
        sm.changeCharacterLook(options[answer])