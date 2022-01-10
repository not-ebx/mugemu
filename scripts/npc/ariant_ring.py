# Queen's Cabinet (2103002) | King's Room (260000303) 

queensRing = 3923
treasure = 4031578

if sm.hasQuest(queensRing):
    if sm.canHold(treasure) and not sm.hasItem(treasure):
        sm.giveItem(treasure)
        sm.sendSayOkay("You carefully opened the chest and took out a ring. "
        "You better get out of here now...")
    elif sm.hasItem(treasure):
        sm.sendSayOkay("You already got a ring. Stealing any more might not be wise...")
    else:
        sm.sendSayOkay("Please make room in your Etc. inventory.")
else:
    sm.sendSayOkay("Stealing from the cabinet will alert the palace guards. Best to leave it alone...")