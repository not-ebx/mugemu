# Eurek the Alchemist (2040050)

from net.swordie.ms.constants import JobConstants

echoDict = {
    112: 1005, # Hero
    122: 1005, # Paladin
    132: 1005, # Dark Knight
    212: 1005, # F/P
    222: 1005, # I/L
    232: 1005, # Bishop
    312: 1005, # Bowmaster
    322: 1005, # Marksman
    412: 1005, # Night Lord
    422: 1005, # Shadower
    434: 1005, # Dual Blade
    512: 1005, # Buccaneer
    522: 1005, # Corsair
    532: 1005, # Cannoneer
    572: 1005, # Jett
    1112: 10001005, # Dawn Warrior
    1212: 10001005, # Blaze Wizard
    1312: 10001005, # Wind Archer
    1412: 10001005, # Night Walker
    1512: 10001005, # Thunder Breaker
    2112: 20001005, # Aran
    2218: 20011005, # Evan
    2312: 20021005, # Mercedes
    2412: 20031005, # Phantom
    2512: 20051005, # Shade
    2712: 20041005, # Luminous
    3112: 30011005, # Demon Slayer
    3122: 30011005, # Demon Avenger
    3212: 30001005, # Battle Mage
    3312: 30001005, # Wild Hunter
    3512: 30001005, # Mechanic
    3712: 30001005, # Blaster
    3612: 30021005, # Xenon
    4112: 40011005, # Hayato
    4212: 40021005, # Kanna
    5112: 50001005, # Mihile
    6112: 60001005, # Kaiser
    6512: 60011005, # Angelic Buster
    10112: 100001005, # Zero
    14212: 140001005 # Kinesis
}

selection = sm.sendNext("Hi, how can I help you? #b\r\n"
            "#L0#Learn Echo of Hero/Exclusive Spell#l")

if selection == 0:
    if chr.getLevel() >= 200:
        currentJob = chr.getJob()
        if currentJob in echoDict:
            echo = echoDict[currentJob]
            if sm.hasSkill(echo):
                sm.sendSayOkay(''.join(["Hm...It looks like you have #s", repr(echo), "# #q", repr(echo), "# already."]))
            else:
                response = sm.sendAskYesNo(''.join(["Greetings, hero. Would you like to learn #s", repr(echo), "# #q", repr(echo), "#?"]))
                if response:
                    sm.giveSkill(echo)
                    sm.sendSayOkay(''.join(["You have learned #s", repr(echo), "# #q", repr(echo), "#."]))
        elif JobConstants.isBeastTamer(currentJob):
            sm.sendSayOkay("Unfortunately, I can't offer Echo of Hero to Beast Tamers.")
        else:
            sm.sendSayOkay("Sorry, I can't grant the skill to those without proper qualifications. \r\n"
            "Come back after finishing your job advancements.")
    else:
        sm.sendSayOkay("You don't have the makings of a hero. Speak to me again when you're at least Level 200.")
