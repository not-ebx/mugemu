# The Remnants of Horntail... (3714)

from net.swordie.ms.enums import Stat

nineSpirit = 2081011

# 25 WATT, 35 MATT, and 300 DEF buff for an hour (ensures it's not one-time from Dragon Squad's Mission)
nineSpiritBreath = 2022109
dragonStone = 2041200
nineSpiritEgg = 4001094

sm.setSpeakerID(nineSpirit)
sm.sendNext("Kyuuu... Kaoooo... #b\r\n\r\n"
"#L0#(He's so cute! I approach the baby dragon.)#l")

if not sm.hasItem(dragonStone):
    response = sm.sendAskAccept("Kyu...Krrr!...Krrr!...Keh! #b\r\n\r\n"
    "(He's coughing like there's something stuck in his throat. Do I help him spit it out?)")
    if response:
        if sm.canhold(dragonStone):
            sm.sendNext(''.join(["The baby dragon spat out a red stone. \r\n"
            "#fUI/UIWindow2.img/QuestIcon/4/0# \r\n"
            "#i", repr(dragonStone), "# #z", repr(dragonStone), "# x 1"]))

            sm.consumeItem(nineSpiritEgg)
            sm.giveItem(dragonStone)
            sm.useItem(nineSpiritBreath)
            sm.giveExp(597957)
            chr.addTraitExp(Stat.senseEXP, 80)
        else:
            sm.sendSayOkay("Krr... #b\r\n\r\n"
            "(The baby dragon shakes his head and backs away as I reach out to him. I might need space in my Use inventory first...)")
else:
    sm.sendNext("Kyu.. Kaooo.. Kaooo.. #b\r\n\r\n"
    "(The baby dragon has cute eyes. He seems to be watching me attentively because I already have a Dragon Stone.)")
