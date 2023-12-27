# El Nath Town
# Portal to house next to FM

from net.mugeemu.ms.constants import BossConstants

sm.setSpeakerID(9000185) # Eileen next to the portal

if sm.sendAskYesNo("Would you like to go to the Zakum quest entrance?"):
	sm.warp(BossConstants.ZAKUM_JQ_MAP_1) # Shammos's Solitary Room 