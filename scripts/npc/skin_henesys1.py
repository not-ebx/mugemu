# Ms. Tan (1012105) | Henesys Skin-Care (100000105)

options = []
#These values will crash the client when attempting to load them onto character
nullSkins = [6, 7, 8]

for skin in range(14):
    #Skip past null skin values
    if skin in nullSkins:
        continue
    options.append(skin)

sm.sendNext("Well, hello! Welcome to the Henesys Skin-Care! "
            "Would you like to have a firm, tight, healthy looking skin like mine? "
            "With a #b#t5153000##k, you can let us take care of the rest and have the kind of skin you've always wanted!")

answer = sm.sendAskAvatar("We have the latest in beauty equipment."
"With our technology, you can preview what your skin will look like in advance!"
"Which treatment would you like?", False, False, options)
if answer < len(options):
    sm.changeCharacterLook(options[answer])
