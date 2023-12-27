package net.mugeemu.ms.world.shop.result;

import net.mugeemu.ms.connection.OutPacket;

/**
 * Created on 3/29/2018.
 */
public interface ShopResult {

    ShopResultType getType();

    void encode(OutPacket outPacket);
}
