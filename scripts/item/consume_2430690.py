# [Pet Box] Blackheart (2430690)

medina = 2183001

prizes = [5000217, 1802354]

sm.setSpeakerID(medina)
selString = ["Would you like Hilla's beloved Blackheart, or earrings for the pet? #b\r\n"]
# Construct selectable items to exchange
for index, reward in enumerate(prizes):
    selString.append(''.join(["#L", repr(index), "##i", repr(reward), "# #z", repr(reward), "##l\r\n"]))
selection = sm.sendNext(''.join(selString))
selectedReward = prizes[selection]

if sm.canHold(selectedReward):
    sm.consumeItem()
    sm.giveItem(selectedReward)
else:
    sm.sendSayOkay("Please check if you have room in your Equip and Cash inventories.")