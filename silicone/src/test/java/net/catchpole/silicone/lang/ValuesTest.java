package net.catchpole.silicone.lang;

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
    }
}
