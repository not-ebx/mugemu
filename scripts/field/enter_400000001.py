# Pantheon: Great Temple Interior (400000001)

formerKaiser = 3000011

# Unhide Former Kaiser if character is a 4th Job Kaiser
if chr.getJob() == 6112:
    sm.hideNpcByTemplateId(formerKaiser, False)