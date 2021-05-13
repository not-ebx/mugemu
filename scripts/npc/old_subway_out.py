# Exit (9201222)

response = sm.sendAskYesNo("Do you want to leave?")
if response:
    sm.warp(103020000)
