package net.mugeemu.ms.connection.netty;

import net.mugeemu.ms.client.Client;

import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {
    private final ConcurrentHashMap<String, Client> clients = new ConcurrentHashMap<>();

    public void registerClient(String clientId, Client client) {
        clients.put(clientId, client);
    }

    public Client getClient(String clientId) {
        return clients.get(clientId);
    }

    public void unregisterClient(String clientId) {
        clients.remove(clientId);
    }
}
