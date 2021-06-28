# Momijigaoka (807000000)

from net.swordie.ms.constants import JobConstants

# Relevant to Hayato only. For some reason, Kanna is unaffected.
# Certain NPCs in Momijigaoka are meant to be unhidden as Hayato progresses through his 1st job questline.
# Since Hayato's quests are not completely scripted, this script will bypass certain npc-enabling quests by leveling.
# This will allow Hayato to see the quests' corresponding NPCs, and accept his mount quest from Shingen.

questList = [
    (57113, 17), # Cutting a Swath
    (57119, 21), # Fox Tail Mystery
    (57141, 29), # Honnou-ji Infiltration 2 [Hayato]
    (57143, 30) # Internal Affairs [Hayato]
]

job = chr.getJob()

if JobConstants.isHayato(job):
    currentLevel = chr.getLevel()
    for quest in questList:
        if currentLevel >= quest[1] and not sm.hasQuestCompleted(quest[0]):
            sm.completeQuestNoRewards(quest[0])