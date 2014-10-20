package net.catchpole.silicone.lang.reflect;

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

import java.util.Arrays;

public class MapperTest {
    @Test
    public void testMapper() {
        Mapper mapper = new Mapper();

        TestCase.assertEquals("1", mapper.getStringValue(new Integer(1)));
        TestCase.assertEquals(new Integer(1), mapper.getObjectValue(Integer.class, "1"));

        TestCase.assertEquals("0001", mapper.getStringValue(new byte[] { 0, 1 }));
        TestCase.assertTrue(Arrays.equals(new byte[] { 0, 1 }, mapper.getObjectValue(byte[].class, "0001")));
    }
}
