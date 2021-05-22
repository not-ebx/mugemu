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

sm.flipDialoguePlayerAsSpeaker()
sm.sendNext("This feeling...")
sm.sendSay("It's something I've never felt before as a commander.")
sm.sendSay("I feel like I've gained new power as well.")
sm.sendSay("I will never forget my past and mission.")
sm.sendSay("I will exterminate all evil and atone for my sins so that no one needs to walk down my path.")

sm.completeQuest(parentID)
sm.giveSkill(echo)
sm.giveItem(medal)