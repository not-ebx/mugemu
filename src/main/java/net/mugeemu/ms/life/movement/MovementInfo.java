package net.mugeemu.ms.life.movement;

import net.mugeemu.ms.connection.Encodable;
import net.mugeemu.ms.connection.InPacket;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.util.Position;
import net.mugeemu.ms.client.character.Char;
import net.mugeemu.ms.life.Life;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sjonnie
 * Created on 8/16/2018.
 */
public class MovementInfo implements Encodable {
    private static final Logger log = Logger.getLogger(MovementInfo.class);

    private int encodedGatherDuration;
    private Position oldPos;
    private Position oldVPos;
    private List<Movement> movements = new ArrayList<>();
    private byte keyPadState;

    public MovementInfo(Position oldPos, Position oldVPos) {
        this.oldPos = oldPos;
        this.oldVPos = oldVPos;
    }

    public MovementInfo(InPacket inPacket) {
        decode(inPacket);
    }

    public void applyTo(Char chr) {
        for (Movement m : getMovements()) {
            m.applyTo(chr);
        }
    }

    public void applyTo(Life life) {
        for (Movement m : getMovements()) {
            m.applyTo(life);
        }
    }

    public void decode(InPacket inPacket) {
        //encodedGatherDuration = inPacket.decodeInt();
        oldPos = inPacket.decodePosition();
        oldVPos = inPacket.decodePosition();
        movements = parseMovement(inPacket);
        keyPadState = inPacket.decodeByte();
    }

    @Override
    public void encode(OutPacket outPacket) {
        //outPacket.encodeInt(encodedGatherDuration);
        outPacket.encodePosition(oldPos);
        outPacket.encodePosition(oldVPos);
        outPacket.encodeByte(movements.size());
        for(Movement m : movements) {
            m.encode(outPacket);
        }
        outPacket.encodeByte(keyPadState);
    }

    private static List<Movement> parseMovement(InPacket inPacket) {
        List<Movement> res = new ArrayList<>();
        byte size = inPacket.decodeByte();
        for (int i = 0; i < size; i++) {
            byte type = inPacket.decodeByte();
            switch (type) {
                case 0:
                case 7:
                case 14:
                case 16:
                case 45:
                case 46:
                    log.info("Normal Movment");
                    res.add(new MovementNormal(inPacket, type));
                    break;
                case 1:
                case 2:
                case 15:
                case 18:
                case 19:
                case 21:
                case 40:
                case 41:
                case 42:
                case 43:
                    log.info("Jump");
                    res.add(new MovementJump(inPacket, type));
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 13:
                case 17:
                case 22:
                    log.info("Teleport");
                    res.add(new MovementTeleport(inPacket, type));
                    break;
                case 11:
                    log.info("Stat Change");
                    res.add(new MovementStatChange(inPacket, type));
                    break;
                case 20:
                    log.info("Fall Down");
                    res.add(new MovementStartFallDown(inPacket, type));
                    break;
                case 44:
                    log.info("Flyuing Block");
                    res.add(new MovementFlyingBlock(inPacket, type));
                    break;
                default:
                    log.warn(String.format("Unhandled move path attribute %s.", type));
                    break;
            }
        }
        return res;
    }

    public byte getKeyPadState() {
        return keyPadState;
    }

    public List<Movement> getMovements() {
        return movements;
    }
}
