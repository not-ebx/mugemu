# Sensitive Squaroid (2155009) | Haven (310070000)

diffusionLine = 4001842
lotusExtraordinary = 4001843
absoCoin = 4310156

diffusionQuantity = sm.getQuantityOfItem(diffusionLine)
lotusQuantity = sm.getQuantityOfItem(lotusExtraordinary)

if diffusionQuantity >= 50 and lotusQuantity >= 1:
    # How many coins can the user exchange up to?
    diffusionQuotient = diffusionQuantity // 50
    purchaseCap = min(diffusionQuotient, lotusQuantity, 100)
    
    sm.sendNext("I think you have what I need...")
    sm.sendNext(''.join(["Give me 50 #i", repr(diffusionLine), "##z", repr(diffusionLine),
    "# and 1 #i", repr(lotusExtraordinary), "##z", repr(lotusExtraordinary), "#,\r\n"
    "and I'll give you 1 #i", repr(absoCoin), "##z", repr(absoCoin), "#..."
    "Do we have a deal?\r\n"
    "#L0# #i", repr(absoCoin), "##z", repr(absoCoin), "##l"]))

    quantityString = ''.join(["You can get up to ", repr(purchaseCap), " #b#z", repr(absoCoin), "#(s)#k..."
    "How many do you want to trade?\r\n"
    "(#t", repr(diffusionLine), "# in your possession: ", repr(diffusionQuantity), ")\r\n"
    "(#t", repr(lotusExtraordinary), "# in your possession: ", repr(lotusQuantity), ")\r\n"])
    quantity = sm.sendAskNumber(quantityString, 1, 1, purchaseCap)

    if not sm.canHold(absoCoin):
        sm.sendSayOkay("Please make room in your Etc. inventory.")
    else:
        sm.consumeItem(diffusionLine, quantity*50)
        sm.consumeItem(lotusExtraordinary, quantity)
        sm.giveItem(absoCoin, quantity)
else:
    sm.sendSayOkay(''.join(["Come back when you have 50 #i", repr(diffusionLine), "##z", repr(diffusionLine),
    "# and a #i", repr(lotusExtraordinary), "##z", repr(lotusExtraordinary), "#."]))