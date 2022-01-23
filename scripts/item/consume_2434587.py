# Piece of Destruction (2434587)

from net.swordie.ms.constants import JobConstants
from net.swordie.ms.constants import ItemConstants

def exchangePieces(gear):
    if not sm.hasItem(parentID, 15):
        sm.sendSayOkay(''.join(["You need #b15 #i", repr(parentID), "# #z", repr(parentID), "##k to exchange for #b#i", repr(gear), "# #z", repr(gear), "##k."]))
    elif not sm.canHold(gear):
        sm.sendSayOkay("Please make room in your Equip inventory.")
    else:
        sm.consumeItem(parentID, 15)
        sm.giveItem(gear)

admin = 2007

masterList = [
    1212063, # Mana Cradle (Shining Rod)
    1222058, # Angelic Shooter (Soul Shooter)
    1232057, # Death Bringer (Desperado)
    1242060, # Split Edge (Thief) (Whip Blade)
    1242061, # Split Edge (Pirate) (Whip Blade)
    1252015, # Scepter
    1262016, # Psy-limiter
    1302275, # Mistilteinn (1H Sword)
    1312153, # Twin Cleaver (1H Axe)
    1322203, # Guardian Hammer (1H Blunt)
    1332225, # Damascus (Dagger)
    1342082, # Rapid Edge (Katara)
    1362090, # Claire Ciel (Cane)
    1372177, # Mana Taker (Wand)
    1382208, # Mana Crown (Staff)
    1402196, # Penitent Tears (2H Sword)
    1412135, # Battle Cleaver (2H Axe)
    1422140, # Lightning Striker (2H Blunt)
    1432167, # Brionak (Spear)
    1442223, # Moon Glaive (Polearm)
    1452205, # Wind Chaser (Bow)
    1462193, # Windwing Shooter (Crossbow)
    1472214, # Risk Holder (Claw)
    1482168, # Perry Talon (Knuckle)
    1492179, # Zeliska (Gun)
    1522094, # Dual Windwing (Dual Bowguns)
    1532098, # Lost Cannon (Hand Cannon)
    1542063, # Raven Ring (Katana)
    1552063, # Indigo Flash (Fan)
    1582016 # Big Mountain (Arm Cannon)
]
# Determine the character's usable weapon types
job = chr.getJob()
usableWeapons = JobConstants.JobEnum.getJobById(job).getUsingWeapons()
weaponTypes = []
for type in usableWeapons:
    weaponTypes.append(type.getVal())

# Filter recList based on which weapon type(s) exist in weaponTypes
recList = filter(lambda weapon: ItemConstants.getWeaponType(weapon) in weaponTypes, masterList)

# To use as last option for initial recommendation
viewAll = len(recList)

sm.setSpeakerID(admin)
recString = ["Equipment for your class will be recommended first. #b\r\n"]
# Construct initial recommendations
for index, gear in enumerate(recList):
    recString.append(''.join(["#L", repr(index), "##i", repr(gear), "# #z", repr(gear), "##l\r\n"]))
recString.append(''.join(["#L", repr(viewAll), "#View the entire item list.#l\r\n"]))
recSelection = sm.sendNext(''.join(recString))

# Look at everything
if recSelection == viewAll:
    selString = ["Please select the armor you'd like to receive. #b\r\n"]
    for index, gear in enumerate(masterList):
        selString.append(''.join(["#L", repr(index), "##i", repr(gear), "# #z", repr(gear), "##l\r\n"]))
    selection = sm.sendNext(''.join(selString))

    selectedGear = masterList[selection]
    exchangePieces(selectedGear)
# Picked recommended gear
else:
    selectedGear = recList[recSelection]
    exchangePieces(selectedGear)