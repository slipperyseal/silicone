package net.catchpole.silicone.security;

import junit.framework.TestCase;
import org.junit.Test;

public class AuthenticationHasherTest {
    @Test
    public void testAuthenticationHasher() throws Exception {
        AuthenticationHasher authenticationHasher = new AuthenticationHasher();

        for (int x=0;x<10;x++) {
            Hash hash = authenticationHasher.createHash("Test" + x);
            TestCase.assertTrue(authenticationHasher.validate("Test" + x, hash));
            TestCase.assertFalse(authenticationHasher.validate("FailTest" + x, hash));

            TestCase.assertEquals(24, hash.getHash().length);
            TestCase.assertEquals(24, hash.getSalt().length);
        }
    }
}
