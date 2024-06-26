# Black Heaven Inside: Core (350060160)  |  Stage 1 Lotus Boss  |  Used to spawn the Boss
from net.mugeemu.ms.enums import ObtacleAtomEnum
from net.mugeemu.ms.constants import BossConstants

BLACK_HEAVEN_CORE = 8240103 # Stage 1

sm.spawnMob(BLACK_HEAVEN_CORE, 0, -16, False, 400000000000) # 400b hp


sm.invokeAtFixedRate(0, BossConstants.LOTUS_BLUE_ATOM_EXECUTION_DELAY, 0,
                     "createObstacleAtom", ObtacleAtomEnum.LotusBlueDebris, 1, BossConstants.LOTUS_BLUE_ATOM_DAMAGE, BossConstants.LOTUS_OBSTACLE_ATOM_VELOCITY, BossConstants.LOTUS_BLUE_ATOM_AMOUNT, BossConstants.LOTUS_BLUE_ATOM_PROP)

sm.invokeAtFixedRate(250, BossConstants.LOTUS_YELLOW_ATOM_EXECUTION_DELAY, 0,
                     "createObstacleAtom", ObtacleAtomEnum.LotusYellowDebris, 2, BossConstants.LOTUS_YELLOW_ATOM_DAMAGE, BossConstants.LOTUS_OBSTACLE_ATOM_VELOCITY, BossConstants.LOTUS_YELLOW_ATOM_AMOUNT, BossConstants.LOTUS_YELLOW_ATOM_PROP)

sm.invokeAtFixedRate(500, BossConstants.LOTUS_PURPLE_ATOM_EXECUTION_DELAY, 0,
                     "createObstacleAtom", ObtacleAtomEnum.LotusPurpleDebris, 3, BossConstants.LOTUS_PURPLE_ATOM_DAMAGE, BossConstants.LOTUS_OBSTACLE_ATOM_VELOCITY, BossConstants.LOTUS_PURPLE_ATOM_AMOUNT, BossConstants.LOTUS_PURPLE_ATOM_PROP)
