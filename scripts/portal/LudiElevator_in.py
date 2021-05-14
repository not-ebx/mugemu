# Helios Tower 99F <=> 2F

ludiSide = 222020200
kftSide = 222020100

if sm.getFieldID() == ludiSide:
    sm.warp(kftSide)
else:
    sm.warp(ludiSide)