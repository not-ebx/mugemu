# Clothes Collector (1012121) | Henesys Market

from net.swordie.ms.enums import InvType

nxItems = []

equipInv = chr.getInventoryByType(InvType.EQUIP)
invItems = equipInv.getItems()
# Sort by physical inventory slot indices, not item ID
invItems.sort(key=lambda item: item.getBagIndex())
# Filter the player's Equip inventory to just Cash Equips
invItems = filter(lambda item: item.isCash(), invItems)

# Make sure to add the item's ID to nxItems, *not* the item entity itself
for equip in invItems:
    equipId = equip.getItemId()
    nxItems.append(equipId)

# No Cash Equips found
if len(nxItems) == 0:
    sm.sendSayOkay("You don't have anything for the Clothes Collector.")
else:
    disposeString = ["Select the clothes you want to throw away. #b\r\n"]
    # Construct disposal list
    for index, nxItem in enumerate(nxItems):
        disposeString.append(''.join(["#L", repr(index), "##i", repr(nxItem), "# #z", repr(nxItem), "##l\r\n"]))
    selection = sm.sendNext(''.join(disposeString))

    # Pull out item id from nxItems
    toDispose = nxItems[selection]

    response = sm.sendAskYesNo(''.join(["Are you sure you want to throw away #b#i", repr(toDispose), "# #z", repr(toDispose), "##k? "
    "Once they're gone, they're gone for good!"]))
    if response:
        sm.consumeItem(toDispose)
        sm.sendSayOkay("Thank you for your garbage.")