# Trend Zone Metropolis (550000000) / Ardentmill (910001000) => Free Market

town = sm.getFieldID()

if town == 910001000:
    # Save Ardentmill map id to QRValue
    sm.addQRValue(9999, "910001000")
else:
    sm.setReturnField()
    sm.setReturnPortal()
sm.warp(910000000, 36)