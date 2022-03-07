# Dao (9000080) | Ravana's Golden Altar

if not sm.getFieldID() == 252030000:
	if sm.hasMobsInField():
		message = "Ravana is still alive; do you really want to leave now?"
	else:
		message = "Congratulations on defeating Ravana. Would you like to leave now?"
	if sm.sendAskYesNo(message):
		sm.chat("You are leaving Ravana's Golden Altar.")
		sm.warpInstanceOut(252030000)
else:
	sm.sendSayOkay("Enter if you wish.")