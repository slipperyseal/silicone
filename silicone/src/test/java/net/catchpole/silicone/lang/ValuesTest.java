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

public class ValuesTest {
    @Test
    public void testToHexString() {
        Values values = new Values();
        TestCase.assertEquals("abcdef01", values.toHexString(0xabcdef01));
        TestCase.assertEquals("00001234", values.toHexString(0x00001234));
        TestCase.assertEquals("ffffffff", values.toHexString(-1));
        TestCase.assertEquals("00000000", values.toHexString(0));

        TestCase.assertEquals("0000000000000000", values.toHexString(0L));
        TestCase.assertEquals("abcdef0123456789", values.toHexString(0xabcdef0123456789L));
        TestCase.assertEquals("ffffffffffffffff", values.toHexString(-1L));

        byte[] bytes = values.hexToBytes("001f");
        TestCase.assertEquals(2, bytes.length);
        TestCase.assertEquals(0x00, bytes[0]);
        TestCase.assertEquals(0x1f, bytes[1]);
    }
}
