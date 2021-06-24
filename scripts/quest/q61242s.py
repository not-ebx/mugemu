# Title - Master of Onmyoudou (61242)

medal = 1142510
spiritHaku = 80001157
mightyHaku = 80001180
echo = 40021005

if sm.canHold(medal):
    sm.chatScript("You obtained the <Master of Onmyoudou> medal.")
    sm.removeSkill(spiritHaku)
    sm.giveSkill(mightyHaku)
    sm.giveSkill(echo)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)