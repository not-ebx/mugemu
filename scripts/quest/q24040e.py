# Awakening (24040)

heroNoMore = 1142336

sm.flipDialoguePlayerAsSpeaker()
if sm.canHold(heroNoMore):
    sm.sendNext("How can this be? I can't be level 10! "
    "Even level 100 would be ridiculous, but 10?! This must be a nightmare! "
    "That's it! I'll pinch myself to wake up...")
    sm.sendSay("OW! Then this is real? Did the curse do this to me? "
    "I didn't like the Black Mage when he was destryoing Maple World, but this is UNFORGIVEABLE! \r\n\r\n"
    "#fUI/UIWindow2.img/QuestIcon/8/0# 500 exp")

    sm.giveExp(500)
    sm.giveItem(heroNoMore)
    sm.completeQuest(parentID)

    # TO DO: Find the "LEVEL 10!?" balloon message effect
    sm.localEmotion(5, 5000, False)
    
else:
    sm.sendNext("Calm down, Mercedes. Maybe a little breathing room in my inventory would help right now...")
