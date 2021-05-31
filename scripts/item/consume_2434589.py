# Piece of Darkness (2434589)

from net.swordie.ms.constants import JobConstants

def exchangePieces(gear):
    if not sm.hasItem(parentID, 5):
        sm.sendSayOkay("You need #b5 #i" + str(parentID) + "# #z" + str(parentID) + "##k to exchange for #b#i" + str(gear) + "# #z" + str(gear) + "##k.")
    elif not sm.canHold(gear):
        sm.sendSayOkay("Please make room in your Equip inventory.")
    else:
        sm.consumeItem(parentID, 5)
        sm.giveItem(gear)

admin = 2007

masterList = [
    1003172, 1052314, 1072485, 1082295, 1102275, 1152108, # Lionheart Battle set
    1003173, 1052315, 1072486, 1082296, 1102276, 1152110, # Dragon Tail Mage set
    1003174, 1052316, 1072487, 1082297, 1102277, 1152111, # Falcon Wing Sentinel set
    1003175, 1052317, 1072488, 1082298, 1102278, 1152112, # Raven Horn Chaser set
    1003176, 1052318, 1072489, 1082299, 1102279, 1152113 # Shark Tooth Skipper set
]
job = chr.getJob()

recList = []

# Filter recList based on current character class (*not* mutually exclusive because Xenon)
if JobConstants.isWarriorEquipJob(job):
    recList.extend(masterList[:6])
if JobConstants.isMageEquipJob(job):
    recList.extend(masterList[6:12])
if JobConstants.isArcherEquipJob(job):
    recList.extend(masterList[12:18])
if JobConstants.isThiefEquipJob(job):
    recList.extend(masterList[18:24])
if JobConstants.isPirateEquipJob(job):
    recList.extend(masterList[24:])

# To use as last option for initial recommendation
viewAll = len(recList)

sm.setSpeakerID(admin)
recString = "Equipment for your class will be recommended first. #b\r\n"
# Construct initial recommendations
for index, gear in enumerate(recList):
    recString += "#L"+ str(index) + "##i" + str(gear) + "# #z" + str(gear) +"##l\r\n"
recString += "#L"+ str(viewAll) + "#View the entire item list.#l\r\n"
recSelection = sm.sendNext(recString)

# Look at everything
if recSelection == viewAll:
    selString = "Please select the armor you'd like to receive. #b\r\n"
    for index, gear in enumerate(masterList):
        selString += "#L"+ str(index) + "##i" + str(gear) + "# #z" + str(gear) +"##l\r\n"
    selection = sm.sendNext(selString)

    selectedGear = masterList[selection]
    exchangePieces(selectedGear)
# Picked recommended gear
else:
    selectedGear = recList[recSelection]
    exchangePieces(selectedGear)