# Epiphany (25512) | Luminous 4th Job

if chr.getJob() == 2711:
    sm.flipDialoguePlayerAsSpeaker()
    sm.sendNext("(I feel the Light and Dark within me coming together, "
    "merging into a new kind of energy!)")
    sm.sendSay("(I've reached a new level of balance between the Light and Dark.)")

    sm.jobAdvance(2712)
    sm.startQuest(parentID)
    sm.completeQuest(parentID)