package net.mugeemu.ms.handlers;

import net.mugeemu.ms.ServerConstants;
import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.handlers.header.OutHeader;

/**
 * Created on 1/10/2018.
 */
public class ClientSocket {

    public static OutPacket migrateCommand(boolean succeed, short port) {
        OutPacket outPacket = new OutPacket(OutHeader.MIGRATE_COMMAND);

        outPacket.encodeByte(succeed); // will disconnect if false
        if(succeed) {
            outPacket.encodeArr(ServerConstants.SERVER_IP);
            outPacket.encodeShort(port);
            outPacket.encodeInt(0); // ?
        }
        return outPacket;
    }
}
