# Queen's Cabinet (2103002) | King's Room (260000303) 

queensRing = 3923
treasure = 4031578

if(sm.hasQuest(queensRing)):
    sm.sendSayOkay("You carefully opened the chest and took out a ring. "
    + "You better get out of here now...")
    sm.giveItem(treasure)