# Camel Cab (2110005)

ariant = 260000000
ariantNorth = 260020000
magatia = 261000000
sahel = 260020700

if sm.getFieldID() == ariant or sm.getFieldID() == ariantNorth:
    destination = magatia
    query = "Do you want to go to #m" + str(magatia) + "#?"
else:
    destination = ariant
    query = "Do you want to go to #m" + str(ariant) + "#?"

response = sm.sendAskYesNo(query)
if response:
    sm.warp(destination)
