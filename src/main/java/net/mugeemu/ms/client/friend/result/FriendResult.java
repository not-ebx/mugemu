package net.mugeemu.ms.client.friend.result;

import net.mugeemu.ms.connection.OutPacket;
import net.mugeemu.ms.client.friend.FriendType;

/**
 * Created on 3/31/2018.
 */
public interface FriendResult {

    FriendType getType();

    void encode(OutPacket outPacket);
}
