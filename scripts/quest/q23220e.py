# Love Lost, but not Forgotten (23220)

from net.swordie.ms.constants import JobConstants

echo = 30011005
vengeanceIncarnate = 1142345
ultimateAvenger = 1142557

# DS or DA?
demon = chr.getJob()
if JobConstants.isDemonSlayer(demon):
    medal = vengeanceIncarnate
else:
    medal = ultimateAvenger

if sm.canHold(medal):
    sm.flipDialoguePlayerAsSpeaker()
    sm.sendNext("This feeling...")
    sm.sendSay("I've never felt this before when I was a commander.")
    sm.sendSay("I feel like I've also gained a new power.")
    sm.sendSay("I will never forget my past and mission.")
    sm.sendSay("I will vanquish the Black Mage and atone for my sins so that no one needs to walk down my path.")

    sm.completeQuest(parentID)
    sm.giveSkill(echo)
    sm.giveItem(medal)
else:
    sm.flipDialoguePlayerAsSpeaker()
    sm.sendSayOkay("I should make some room in my Equip inventory.")