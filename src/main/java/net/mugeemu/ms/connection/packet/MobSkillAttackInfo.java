package net.mugeemu.ms.connection.packet;

import net.mugeemu.ms.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 3/19/2018.
 */
public class MobSkillAttackInfo {
    public byte actionAndDirMask;
    public byte action;
    public int targetInfo;
    public short skillID;
    public List<Position> multiTargetForBalls = new ArrayList<>();
    public List<Short> randTimeForAreaAttacks = new ArrayList<>();
}
