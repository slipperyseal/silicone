package net.catchpole.silicone.lang;

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

import java.security.MessageDigest;
import java.security.SecureRandom;

public class Uid {
    private byte[] bytes;
    private String value;

    public Uid(byte[] bytes) {
        this.bytes = bytes;
        this.value = new Values().toHexString(bytes);
    }

    public String toString() {
        return value;
    }

    public Uid getHash(byte[] value) {
        return new Uid(createHash(bytes, value));
    }

    private final static String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    private final SecureRandom random = new SecureRandom();

    public byte[] createHash(byte[] input, byte[] addition) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input);
            return md.digest(addition);
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }
}
