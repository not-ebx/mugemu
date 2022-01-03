# Piece of Ruin (2434588)

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
    1212014, # Thanatos (Shining Rod)
    1222014, # Soul Drinker (Soul Shooter)
    1232014, # Painful Destiny (Desperado)
    1242042, # Fallen Queen (Thief) (Whip Blade)
    1242014, # Fallen Queen (Pirate) (Whip Blade)
    1252014, # Scepter
    1262015, # Psy-limiter
    1302152, # Cutlass (1H Sword)
    1312065, # Champion Axe (1H Axe)
    1322096, # Battle Hammer (1H Blunt)
    1332130, # Baselard (Dagger)
    1342036, # Shadow Katara
    1362019, # Crimson Cane
    1372084, # Arc Wand
    1382104, # War Staff
    1402095, # Battle Scimitar (2H Sword)
    1412065, # Battle Axe (2H Axe)
    1422066, # Blast Maul (2H Blunt)
    1432086, # Fuscina (Spear)
    1442116, # Partisan (Polearm)
    1452111, # Composite Bow
    1462099, # Heavy Crossbow
    1472122, # Metal Fist (Claw)
    1482084, # Wild Talon (Knuckle)
    1492085, # Sharpshooter (Gun)
    1522018, # Dual Bowguns
    1532018, # Supernova (Hand Cannon)
    1542015, # Red King (Katana)
    1552015, # Fan
    1582015 # Valore (Arm Cannon)
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