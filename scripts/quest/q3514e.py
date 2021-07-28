# The Sorcerer Who Sells Emotions (3514)

from net.swordie.ms.enums import Stat

sorcerer = 2140002

# Apparently the script is also supposed to check if you're below 20% of your Max HP before leading into the dialogue below
# This is...really finicky since the Sorcerer's Potion tries to kill you using only your base HP, which doesn't take any HP bonuses into account
# Thus, the HP check will be skipped over entirely
# Side effect: you can just drop the potion and it still counts for finishing the quest
sm.setSpeakerID(sorcerer)
sm.sendNext("Have you taken the medicine? How was it? The medicine made by me is always perfect!")
sm.sendSay("What? ...all you have to do is reduce your HP? Hm, hmm. "
"Who is it? Who told you such nonsense? That can't be true! Hahahaha! \r\n\r\n"
"#fUI/UIWindow2.img/QuestIcon/11/0# Willpower 50\r\n"
"#fUI/UIWindow2.img/QuestIcon/8/0# 4,916,000 EXP")

chr.addTraitExp(Stat.willEXP, 50)
sm.giveExp(4916000)
sm.completeQuest(parentID)