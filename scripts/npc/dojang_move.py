# Mu Lung Dojo Bulletin Board (2091006) | Mu Lung Temple (250000100)

dojo = 925020000

response = sm.sendAskYesNo("Would you like to go to Mu Lung Dojo?")

if response:
    sm.setReturnField()
    sm.setReturnPortal(0)
    sm.warp(dojo)