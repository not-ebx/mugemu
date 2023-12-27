from net.mugeemu.ms.util import Position
from net.mugeemu.ms.connection.packet import Effect
from net.mugeemu.ms.connection.packet import User
from net.mugeemu.ms.enums import TextEffectType

sm.lockInGameUI(True)

effect = Effect.createFieldTextEffect("#fs20#- After the forest and house have recovered in a couple of months -", 50, 2000, 4,
                                      Position(0, 0), 1, 4 , TextEffectType.BlackFadedBrush, 0, 0)
sm.getChr().getField().broadcastPacket(User.effect(effect));
sm.sendDelay(4000)

sm.lockInGameUI(False)