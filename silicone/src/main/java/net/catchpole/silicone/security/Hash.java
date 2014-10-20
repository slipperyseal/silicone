package net.catchpole.silicone.security;

public class Hash {
    private byte[] salt;
    private byte[] hash;

    public Hash(byte[] salt, byte[] hash) {
        this.salt = salt;
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getHash() {
        return hash;
    }
}