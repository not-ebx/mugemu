# Pharmacy Drawer 4 (1052132) | Kerning City Pharmacy (103000002)

middleman = 2852
firstAid = 4033036

if sm.hasQuest(middleman):
    if sm.canHold(firstAid) and not sm.hasItem(firstAid):
        sm.giveItem(firstAid)
        sm.sendSayOkay("(You searched the drawer and took out an emergency kit.)")
    elif sm.hasItem(firstAid):
        sm.sendSayOkay("(The drawer is now empty.)")
    else:
        sm.sendSayOkay("Please make room in your Etc. inventory.")
else:
    sm.sendSayOkay("Do not open without permission.")