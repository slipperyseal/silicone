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

import junit.framework.TestCase;
import net.catchpole.silicone.lang.TimeCache;
import org.junit.Test;

import java.lang.reflect.Field;

public class CacheSessionPersistTest {
    @Test
    public void testCacheSessionPersist() throws Exception {
        MemorySessionPersist memorySessionPersist = new MemorySessionPersist();
        CacheSessionPersist cacheSessionPersist = new CacheSessionPersist(memorySessionPersist, 800);
        cacheSessionPersist.setSession("key", "value");
        TimeCache timeCache = (TimeCache)getPrivate(cacheSessionPersist, "timeCache");

        // set
        TestCase.assertEquals("value", cacheSessionPersist.getSession("key"));
        TestCase.assertEquals("value", memorySessionPersist.getSession("key"));
        TestCase.assertEquals("value", timeCache.get("key"));

        // clear
        Thread.sleep(1000);
        TestCase.assertNull(timeCache.get("key"));

        // reset
        TestCase.assertEquals("value", cacheSessionPersist.getSession("key"));
        TestCase.assertEquals("value", memorySessionPersist.getSession("key"));
        TestCase.assertEquals("value", timeCache.get("key"));

        // delete
        cacheSessionPersist.delete("key");
        TestCase.assertNull(cacheSessionPersist.getSession("key"));
        TestCase.assertNull(memorySessionPersist.getSession("key"));
        TestCase.assertNull(timeCache.get("key"));
    }

    private Object getPrivate(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}
