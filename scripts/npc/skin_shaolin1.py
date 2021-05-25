# Fuzhi (9310043) | Mount Song Hamlet (701210000)

options = []
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
