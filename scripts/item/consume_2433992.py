# 돼지바 라이딩 영구 이용권 [Permanent Pig Bar Mount Coupon] (2433992)

mount = 80001771

if sm.hasSkill(mount):
    sm.chat("You already have the 'Pig Bar' mount.")
else:
    sm.consumeItem()
    sm.giveSkill(mount)
    sm.chat("Successfully added the 'Pig Bar' mount.")
