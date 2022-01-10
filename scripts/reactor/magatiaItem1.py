# Zenumist Magic Pillar (2612004) | Zenumist Society (261000010)

bottle = 4031705
treeBranch = 4031703

drop = sm.getDropInRect(bottle, 200)
if drop is not None:
    sm.dropItem(treeBranch, -119, -111)
    field.removeDrop(drop.getObjectId(), 0, False, -1)
    