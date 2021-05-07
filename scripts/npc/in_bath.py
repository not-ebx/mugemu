# Hikari (9120003) | Showa Town (801000000)

#Grab character gender
gender = chr.getAvatarData().getAvatarLook().getGender()
spaF = 801000200
spaM = 801000100

response = sm.sendAskYesNo("Would you like to enter the bathhouse? That'll be 300 mesos for you. And don't take the towels!")

if response:
    if sm.getMesos() >= 300:
        sm.deductMesos(300)
        if gender == 1:
            sm.warp(spaF)
        else:
            sm.warp(spaM)
    else:
        sm.sendSayOkay("Come back when you have mesos!")