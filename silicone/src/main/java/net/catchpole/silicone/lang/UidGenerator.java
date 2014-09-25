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

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UidGenerator {
    private static final SecureRandom random = new SecureRandom();

    public String getUid(int byteLength) {
        return new Values().toHexString(getUidBytes(byteLength));
    }

    public String getSecurityUid() {
        return getUid(20);
    }

    public String getPublicUid() {
        return getUid(8);
    }

    public byte[] getUidBytes(int byteLength) {
        byte[] uid = new byte[byteLength];
        random.nextBytes(uid);
        return uid;
    }

    public String getTimeUid() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormatter.format(new Date()) + getUid(4);
    }
}
