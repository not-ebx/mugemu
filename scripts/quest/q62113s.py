# Just a Little Test 2 (62113)

from net.swordie.ms.enums import InvType

huangZhen = 9310536
zhenLong = 9310532

redBeanPorridge = 4034643
garlic = 4034656

if sm.getEmptyInventorySlots(InvType.ETC) >= 2:
    if not sm.hasItem(redBeanPorridge):
        sm.giveItem(redBeanPorridge)
    if not sm.hasItem(garlic):
        sm.giveItem(garlic)
    sm.startQuest(62113)

    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("This town is officially weirding me out. You guys are all freaks.")

    sm.setSpeakerID(huangZhen)
    sm.setBoxChat()
    sm.sendNext("Wonderful! You pass! #p" + repr(zhenLong) + "# can explain everything, "
    "but let me be the first to welcome you to Yu Garden, my fellow human!")

    sm.flipBoxChat()
    sm.flipBoxChatPlayerAsSpeaker()
    sm.sendNext("What in the heck?! That was the dumbest-- You guys are the most-- Ugh! "
    "I can't even talk right now!")

    sm.setSpeakerID(huangZhen)
    sm.setBoxChat()
    sm.sendNext("It'll all make sense when #p" + repr(zhenLong) + "# explains it to you.")
else:
    sm.sendNext("Talk to me again after you free up 2 slots in your Etc. inventory.")