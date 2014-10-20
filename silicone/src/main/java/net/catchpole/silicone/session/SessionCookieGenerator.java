package net.catchpole.silicone.session;

//   Copyright 2014 catchpole.net
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

import net.catchpole.silicone.lang.UidGenerator;
import net.catchpole.silicone.lang.Values;
import net.catchpole.silicone.security.AuthenticationHasher;
import net.catchpole.silicone.security.Hash;

public class SessionCookieGenerator {
    private final AuthenticationHasher authenticationHasher = new AuthenticationHasher();
    private final UidGenerator uidGenerator = new UidGenerator();
    private final Values values = new Values();
    private final SessionHashPersist sessionHashPersist;

    public SessionCookieGenerator(SessionHashPersist sessionHashPersist) {
        this.sessionHashPersist = sessionHashPersist;
    }

    public String generateSessionCookie() {
        SessionHash sessionHash = new SessionHash();
        String key = uidGenerator.getSecurityUid();
        sessionHash.setKey(key);

        String token = uidGenerator.getSecurityUid();
        Hash hash = authenticationHasher.createHash(token);
        sessionHash.setSalt(values.toHexString(hash.getSalt()));
        sessionHash.setHash(values.toHexString(hash.getHash()));

        sessionHashPersist.persist(sessionHash);

        return key + token;
    }

    public void delete(String cookie) {
        if (cookie == null || cookie.length() != 80) {
            return;
        }

        sessionHashPersist.delete(cookie.substring(0, 40));
    }

    public String validateKey(String cookie) {
        if (cookie == null || cookie.length() != 80) {
            return null;
        }

        SessionHash sessionHash = sessionHashPersist.fetch(cookie.substring(0, 40));
        if (sessionHash != null) {
            if (authenticationHasher.validate(cookie.substring(40), new Hash(
                    values.hexToBytes(sessionHash.getSalt()),
                    values.hexToBytes(sessionHash.getHash())))) {
                System.out.println("validate ok " + cookie);
                return sessionHash.getKey();
            }
        }
        return null;
    }
}
