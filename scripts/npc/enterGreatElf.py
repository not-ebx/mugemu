# Elf's Harp (1033211) | King's Seat (101050010)

from net.swordie.ms.constants import JobConstants

advent = 910150100

#Check if the character is a Mercedes
merc = chr.getJob()

if JobConstants.isMercedes(merc):
    sm.warpInstanceIn(advent, False)
else:
    sm.chat("You tried to play the Elf's Harp, but nothing happens.")