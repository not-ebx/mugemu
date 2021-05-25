# Olaf (1002101) | Lith Harbor (104000000)

from net.swordie.ms.constants import JobConstants

# Check character's job category
adv = chr.getJob()

if JobConstants.isAdventurer(adv):
    response = sm.sendAskYesNo("Would you like to go back to #bMaple Island#k?")
    if response:
        sm.warp(2000100)
else:
    sm.sendSayOkay("Sorry, only Explorers can go to #bMaple Island#k.")