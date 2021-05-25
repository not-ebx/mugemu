# 200020001
if 2400 == chr.getJob():
    sm.warpInstanceIn(915010000, 1, False)
elif 2410 <= chr.getJob() <= 2412:
    sm.warp(915020000, 2)
else:
    sm.chat("Only Phantoms can enter.")
sm.dispose()
