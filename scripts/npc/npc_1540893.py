# Quartermaster Sakaro (1540893) | Deserted Camp (105300000)

faintStigma = 4001868
twistedStigma = 4001869
stigmaCoin = 4310199

faintQuantity = sm.getQuantityOfItem(faintStigma)
twistedQuantity = sm.getQuantityOfItem(twistedStigma)

if faintQuantity >= 50 and twistedQuantity >= 1:
    # How many coins can the user exchange up to?
    faintQuotient = faintQuantity // 50
    purchaseCap = min(faintQuotient, twistedQuantity, 100)

    sm.sendNext("Ah, a Spirit Stone marked by the stigma of vengeance.")
    sm.sendNext(''.join(["Give to me 50 #i", repr(faintStigma), "##z", repr(faintStigma),
    "# and 1 #i", repr(twistedStigma), "##z", repr(twistedStigma), "#,\r\n"
    "and I'll give you 1 #i", repr(stigmaCoin), "##z", repr(stigmaCoin), "# in return. "
    "What do you say?\r\n"
    "#L0# #i", repr(stigmaCoin), "##z", repr(stigmaCoin), "##l"]))

    quantityString = ''.join(["You can get up to ", repr(purchaseCap), " #b#z", repr(stigmaCoin), "#(s)#k. "
    "How many do you want to trade?\r\n"
    "(#t", repr(faintStigma), "# in your possession: ", repr(faintQuantity), ")\r\n"
    "(#t", repr(twistedStigma), "# in your possession: ", repr(twistedQuantity), ")\r\n"])
    quantity = sm.sendAskNumber(quantityString, 1, 1, purchaseCap)

    if not sm.canHold(stigmaCoin):
        sm.sendSayOkay("Please make room in your Etc. inventory.")
    else:
        sm.consumeItem(faintStigma, quantity*50)
        sm.consumeItem(twistedStigma, quantity)
        sm.giveItem(stigmaCoin, quantity)
else:
    sm.sendSayOkay(''.join(["Come to me when you have 50 #i", repr(faintStigma), "##z", repr(faintStigma),
    "#s and a #i", repr(twistedStigma), "##z", repr(twistedStigma), "#."]))
