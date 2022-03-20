package pt.tecnico.grpc.server;

import pt.tecnico.grpc.Banking.RegisterResponse;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class Bank {
    private static final Logger LOGGER = Logger.getLogger(Bank.class.getName());
    ConcurrentHashMap<String, Integer> clients = new ConcurrentHashMap<>();

    public void register(String client, Integer balance) {
        clients.put(client, balance);
        LOGGER.info("User: " + client + " has been instantiated with balance: " + balance);
    }

    public Integer getBalance(String client) {
        return clients.get(client);
    }

    public boolean isClient(String client) {
        return clients.get(client) != null;
    }
    
    public void fundAll(Integer amount) {

        for (Map.Entry element : clients.entrySet()) {
            String client = (String)element.getKey();
            Integer newBalance = ((Integer)element.getValue() + amount);

            clients.put(client, newBalance);
        }
        }
}
