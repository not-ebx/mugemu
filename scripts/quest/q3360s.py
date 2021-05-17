# Verifying the Password (3360)

import string, random

parwen = 2111006

sm.setSpeakerID(parwen)
sm.sendNext("Oh, you're here! Good thing, because I, Parwen, have found the master key "
"that will allow you to enter the Secret Passage! Isn't it great? Tell me Parwen is great!")
response = sm.sendAskYesNo("Now, the passcode is very long and complex, so I suggest you write it down somewhere. "
"I'm only going to tell you this once, okay? Are you ready?")
if response:
    # Constructing the generated password per quest accept instance
    # The pool of valid characters consists of upper-case letters and digits
    passPool = string.ascii_uppercase + string.digits
    # Randomly generate the password string of length 10 before storing it into the quest's QRvalue for later
    password = "".join(random.choice(passPool) for letter in range(10))
    sm.createQuestWithQRValue(parentID, password)
    sm.sendSayOkay("The passcode is #b" + str(password) + "#k. You didn't forget it, did you? "
    "Enter this passcode at the entrance of Secret Passage, and you will have unlimited access to it.")
else:
    sm.sendSayOkay("You don't have something to write on, do you? Talk to me again when you're ready to get the passcode.")