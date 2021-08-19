# Zenumist Lab - 1st Floor Hallway / Alcadno Lab - Area B-1 => Secret Basement Path

passageSides = {
    261010000: ["ZENUMIST", 2],
    261020200: ["ALCADNO", 1]
}

unlocked = ["ZENUMISTALCADNO", "ALCADNOZENUMIST"]

zenumist = 261010000
alcadno = 261020200
secretPassage = 261030000

passEntry = 3360
passageVoice = 2111024
currentSociety = sm.getFieldID()

if sm.hasQuestCompleted(passEntry):
    sm.chat("Your name is on the list. Now transporting to the Secret Passage.")
    sm.warp(secretPassage, passageSides[currentSociety][1])

elif sm.hasQuest(passEntry):
    password = sm.getQRValue(passEntry)
    societyKey = passageSides[currentSociety][0]
    # Does the current side's key already exist in the password string?
    if societyKey in password:
        sm.chat("The security device is already unlocked on this side.")
    else:
        sm.setSpeakerID(passageVoice)
        answer = sm.sendAskText("Please enter the passcode.", "", 1, 10)
        if answer == password[:10]:
            successStr = "The security device has been unlocked. "
            # Store the current society's key into QRvalue
            password += societyKey
            # Were both sides already unlocked?
            if any(unlockString in password for unlockString in unlocked):
                successStr += "Your name has entered the list."
                password = "1"
            
            sm.chat(successStr)
            sm.setQRValue(passEntry, password, False)
            # If the user is done, their reward is entering the Secret Passage
            if password == "1":
                sm.warp(secretPassage, passageSides[currentSociety][1])
                sm.completeQuest(passEntry)
        else:
            sm.chat("The security device rejected your password.")

else:
    sm.chat("Your name is not on the list.")