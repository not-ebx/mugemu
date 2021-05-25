# Close to the Birds (101020100) => Lania's Front Yard

from net.swordie.ms.constants import JobConstants

lania = 101000101

#Check if the character is a Luminous
lumi = chr.getJob()

if JobConstants.isLuminous(lumi):
    sm.warp(lania, 2)
else:
    sm.chat("There's nothing of interest here.")