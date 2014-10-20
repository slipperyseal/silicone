package net.catchpole.silicone.session;

public interface SessionHashPersist {
    public void persist(SessionHash sessionHash);

    public void delete(String key);

    public SessionHash fetch(String key);
}
