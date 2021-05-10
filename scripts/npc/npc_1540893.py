# Quartermaster Sakaro (2155009) | Deserted Camp (105300000)

faintStigma = 4001868
twistedStigma = 4001869
stigmaCoin = 4310199

faintQuantity = sm.getQuantityOfItem(faintStigma)
twistedQuantity = sm.getQuantityOfItem(twistedStigma)

if faintQuantity >= 50 and twistedQuantity >= 1:
    #How many coins can the user exchange up to?
    faintQuotient = faintQuantity // 50
    if faintQuotient > twistedQuantity:
        purchaseCap = twistedQuantity
    else:
        purchaseCap = faintQuotient
    
    #Max stack sanity check
    if purchaseCap > 100:
        purchaseCap = 100

    sm.sendNext("Ah, a Spirit Stone marked by the stigma of vengeance.")
    sm.sendNext("Give to me 50 #i" + str(faintStigma) + "##z" + str(faintStigma)
    + "# and 1 #i" + str(twistedStigma) + "##z" + str(twistedStigma) + "#,\r\n"
    "and I'll give you 1 #i" + str(stigmaCoin) + "##z" + str(stigmaCoin) + "# in return. "
    "What do you say?\r\n"
    "#L0# #i" + str(stigmaCoin) + "##z" + str(stigmaCoin) + "##l")
    quantity = sm.sendAskNumber("You can get up to " + str(purchaseCap) + " #b#z" + str(stigmaCoin) + "#(s)#k. "
    "How many do you want to trade?\r\n"
    "(#t" + str(faintStigma) + "# in your possession: " + str(faintQuantity) + ")\r\n"
    "(#t" + str(twistedStigma) + "# in your possession: " + str(twistedQuantity) + ")\r\n", 1, 1, purchaseCap)

    if not sm.canHold(stigmaCoin):
        sm.sendSayOkay("Please make room in your Etc. inventory.")
    else:
        sm.consumeItem(faintStigma, quantity*50)
        sm.consumeItem(twistedStigma, quantity)
        sm.giveItem(stigmaCoin, quantity)
else:
    sm.sendSayOkay("Come to me when you have 50 #i" + str(faintStigma) + "##z" + str(faintStigma) +
    "#s and a #i" + str(twistedStigma) + "##z" + str(twistedStigma) + "#.")
