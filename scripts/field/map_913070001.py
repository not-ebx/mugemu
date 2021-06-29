# Limbert's General Store [Mihile Tutorial: Skip] (913070001)

# Constants
QUEST = 20031
MAPLE_ADMINISTARTOR = 2007
EREVE = 913070071

questList = [
    20031, # Manual Labor
    20032, # Mopping Up
    20033, # Tread Carefully in the Back Yard
    20034, # Unfamiliar Guests
    20035, # The Final Test
    20036 # A Knight of Light
]

if not sm.hasQuest(QUEST) and not sm.hasQuestCompleted(QUEST):
    sm.lockInGameUI(True)
    sm.setSpeakerID(MAPLE_ADMINISTARTOR)
    if sm.sendAskYesNo("Would you like to skip the tutorial?"):
        sm.giveItem(1142399)# Newborn Light (Medal)
        sm.giveItem(1052444)# Apprentice Knight of Light Robe
        sm.giveItem(1302077)# Beginner Warrior's Sword
        sm.giveAndEquip(1098000)# Soul Shield of Protection
        sm.removeEscapeButton()
        sm.levelUntil(10)
        sm.jobAdvance(5100)
        sm.addAP(40)
        sm.startQuestNoCheck(29976)
        sm.completeQuestNoRewards(29976)
        for quest in questList:
            sm.completeQuestNoRewards(quest)
        sm.lockInGameUI(False)
        sm.warpInstanceIn(EREVE, 0)
    else:
        sm.chatScript("Mr. Limbert's General Store")
        sm.chatScript("Month 3, Day 7")
        sm.progressMessageFont(3, 20, 20, 0, "Click on the lightbulb to start the quest. Quest Status Hotkey [Q] / Secondary Key [J]")
        sm.sendDelay(1500)
        
        sm.avatarOriented("UI/tutorial.img/34")
        sm.sendDelay(1200)

        sm.lockInGameUI(False)