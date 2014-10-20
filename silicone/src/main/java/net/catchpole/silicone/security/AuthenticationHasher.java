package net.catchpole.silicone.security;

import net.catchpole.silicone.lang.Throw;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class AuthenticationHasher {
    private final static String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    private final static int HASH_SIZE = 24;
    private final static int PBKDF2_ITERATIONS = 1000;
    private final static SecureRandom random = new SecureRandom();
    private final SecretKeyFactory secretKeyFactory;

    public AuthenticationHasher() {
        try {
            this.secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public Hash createHash(String value) {
        try {
            byte[] salt = new byte[HASH_SIZE];
            random.nextBytes(salt);
            return new Hash(salt, generate(value.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_SIZE));
        } catch (Throwable t) {
            throw Throw.unchecked(t);
        }
    }

    public boolean validate(String value, Hash hash) {
        try {
            byte[] hashBytes = hash.getHash();
            return fixedTimeEquals(hashBytes, generate(value.toCharArray(), hash.getSalt(), PBKDF2_ITERATIONS, hashBytes.length));
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    private boolean fixedTimeEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    private byte[] generate(char[] value, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(value, salt, iterations, bytes * 8);
        synchronized (this) {
            return secretKeyFactory.generateSecret(spec).getEncoded();
        }
    }
}
