# [Lv. 250 Reached] The Light of Hope (14469)

throne = 3014005

cygnus = 1101000

sm.setSpeakerID(cygnus)
if sm.canHold(throne):
    sm.flipDialogue()
    sm.sendNext("Do you remember when you began your training? \r\n\r\n"
    "Your skills were not so strong, but your courage was second to none. "
    "I think that courage is what gave you the power to chase your dreams.")

    sm.flipDialogue()
    sm.sendSay("I would like to recognize and honor your efforts by giving you this Throne of Masters chair.")

    sm.completeQuest(parentID)
    # One throne per character, and in case of dialogue flipping
    if not sm.hasItem(throne):
        sm.giveItem(throne)
    
    sm.flipDialogue()
    sm.sendSay("You've gained the #b#i" + str(throne) + "# #z" + str(throne) + "##k! \r\n\r\n"
    "Darkness still threatens Maple World, but it's always darkest before the dawn! "
    "It's up to you, #b#h ##k, to join others to bring the light of hope to our world.")

    sm.flipDialogue()
    sm.sendPrev("I hope that my gift will light your path of honor.")
else:
    sm.sendSayOkay("Please make room in your Set-up inventory.")