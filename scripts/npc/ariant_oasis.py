# Palace Oasis (2103000) | Ariant Castle (260000300)

ariantCulture = 3900

if sm.hasQuest(ariantCulture):
    sm.setQRValue(ariantCulture, "5", False)
    sm.sendSayOkay("You used two hands to drink the clean water of the Oasis. "
    "Delicious! It quenched your thirst right on the spot.")
else:
    sm.sendSayOkay("The Oasis' pure water rests here. "
    "The bottom of the Oasis can be clearly seen upon taking a closer look.")