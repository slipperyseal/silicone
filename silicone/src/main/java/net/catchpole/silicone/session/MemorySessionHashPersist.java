package net.catchpole.silicone.session;

import java.util.HashMap;
import java.util.Map;

public class MemorySessionHashPersist implements SessionHashPersist {
    private Map<String,SessionHash> map = new HashMap<String, SessionHash>();

    @Override
    public synchronized void persist(SessionHash sessionHash) {
        map.put(sessionHash.getKey(), sessionHash);
    }

    @Override
    public synchronized void delete(String key) {
        map.remove(key);
    }

    @Override
    public synchronized SessionHash fetch(String key) {
        return map.get(key);
    }
}
