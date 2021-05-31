# [Lv. 250 Reached] The Light of Hope (14469)

cygnus = 1101000
ereve = 130000000

sm.setSpeakerID(cygnus)
sm.flipDialogue()
response = sm.sendAskYesNo("#b#h ##k, I'm quite amazed at how much you've grown. "
"As a representative of the Explorers, Cygnus Knights, Resistance, Heroes, and Transcendents, "
"I would like to show my respect. \r\n\r\n"
"Would you kindly come to Ereve and speak with me, please?")
if response:
    sm.startQuest(parentID)
    sm.warp(ereve)
    sm.chatScript("You've arrived at Ereve. Talk to Cygnus.")