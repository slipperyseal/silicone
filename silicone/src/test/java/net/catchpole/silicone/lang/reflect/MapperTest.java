package net.catchpole.silicone.lang.reflect;

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
