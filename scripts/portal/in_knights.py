# Knight Stronghold: Stronghold Entrance
# Quest: Scouting the Stronghold

SCOUTING_THE_STRONGHOLD = 31124 #quest
KNIGHT_DISTRICT_1 = 271030100

if sm.hasQuest(SCOUTING_THE_STRONGHOLD):
    sm.chat("Security in the Stronghold has been increased. Getting in won't be easy.")
    sm.setQRValue(SCOUTING_THE_STRONGHOLD, "end", False)
elif sm.hasQuestCompleted(SCOUTING_THE_STRONGHOLD):
    sm.warp(KNIGHT_DISTRICT_1)