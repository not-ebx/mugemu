# Alcadno's Cabinet (2111010) | Dark Lab (926120000)

secretDocument = 4031708

# Player must be within 50 units on the cabinet's x-position, 100 units on its y-position (effectively right next to it, same plat) to be considered "within reach"
currentPos = chr.getPosition()
xDistance = 50
yDistance = 100
if abs(sm.getObjectPositionX() - (currentPos.getX())) < xDistance and abs(sm.getObjectPositionY() - (currentPos.getY())) < yDistance:
    if sm.canHold(secretDocument) and not sm.hasItem(secretDocument):
        sm.giveItem(secretDocument)
        sm.sendNext("(In the midst of darkness, you can feel a bookshelf on your finger tips... "
        "Looking closely, there appears to be a bundle of documents. This must be the document Bedin was talking about. "
        "Secure the document and head back to Bedin.)")
    elif sm.hasItem(secretDocument):
        sm.sendSayOkay("The bookshelf is now empty.")
    else:
        sm.sendNext("Unable to take the book due to your Etc. inventory being full.")
else:
    sm.sendSayOkay("You can't reach it. It's too far.")