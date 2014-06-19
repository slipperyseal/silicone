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

import junit.framework.TestCase;
import org.junit.Test;

public class UidGeneratorTest {
    @Test
    public void testUidGenerator() {
        UidGenerator uidGenerator = new UidGenerator();
        byte[] bytes = uidGenerator.getUidBytes(8);
        TestCase.assertEquals(8, bytes.length);
        TestCase.assertEquals(toHexString(bytes), new Values().toHexString(bytes));
        TestCase.assertEquals(32, uidGenerator.getUid(16).length());

        String uid = uidGenerator.getUid(8);
        TestCase.assertEquals(uid.toUpperCase(), uid);

        for (int x=0;x<20;x++) {
            System.out.println(uidGenerator.getTimeUid());
        }
    }

    public String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length*2);
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
