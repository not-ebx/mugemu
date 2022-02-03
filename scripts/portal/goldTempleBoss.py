# Entrance to Ravana's Altar (252030000)

if sm.checkParty():
    sm.chat("You are being moved to Ravana's Golden Altar.")
    # Redirect to Busted Ravana map if Ravana the Demon is active
    if sm.hasQuest(3863):
        sm.warpInstanceIn(925120100, True)
    # Normal Ravana map
    else:
        sm.warpInstanceIn(252030100, True)