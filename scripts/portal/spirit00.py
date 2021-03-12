# Portal Select | Knight Stronghold: Knight District 2 (271030200)

DAWN_MAP = 271030201
BLAZE_MAP = 271030202
WIND_MAP = 271030203
NIGHT_MAP = 271030204
THUNDER_MAP = 271030205

INFORMANT_NPC = 2143003
THE_DREAM_KEY = 31151

thunderString = "\r\n#L4#Hallowed Ground of Thunder#l" if sm.hasQuestCompleted(THE_DREAM_KEY) else "" #checking if they can access the storm map

maps = [DAWN_MAP, BLAZE_MAP, WIND_MAP, NIGHT_MAP, THUNDER_MAP]
sm.setSpeakerID(INFORMANT_NPC)
select = sm.sendNext("You're planning to go to the Hallowed Ground? Good idea. You'll be able to weaken them by defeating the spirits there."

                "\r\n#b#L0#Hallowed Ground of Dawn#l"
                "\r\n#L1#Hallowed Ground of Blaze#l"
                "\r\n#L2#Hallowed Ground of Wind#l"
                "\r\n#L3#Hallowed Ground of Night#l"
                + thunderString)

sm.warp(maps[select], 0)
sm.dispose()