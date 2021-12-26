# Stone Table (2502002) | Wild Bear Area 1 (250010301)

peach = 2022116
note = 2022252

drop = sm.getDropInRect(peach, 100)
if drop is not None:
    sm.dropItem(note, 894, -93)
    field.removeDrop(drop.getObjectId(), 0, False, -1)
    