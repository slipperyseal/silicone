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

import java.util.HashMap;
import java.util.Map;

public class TimeCache<K,V> {
    private final Map<K,Value<V>> map = new HashMap<K, Value<V>>();
    private final long maxAge;

    public TimeCache(long maxAge) {
        this.maxAge = maxAge;
    }

    public void put(K key, V value) {
        map.put(key, new Value<V>(value));
    }

    public void remove(K key) {
        map.remove(key);
    }

    public V get(K key) {
        Value<V> value = map.get(key);
        if (value == null) {
            return null;
        }
        V v = value.getValue();
        if (v == null) {
            map.remove(key);
        }
        return v;
    }

    class Value<V> {
        private long created;
        private V value;

        Value(V value) {
            this.created = System.currentTimeMillis();
            this.value = value;
        }

        V getValue() {
            if (this.created <= System.currentTimeMillis()-maxAge) {
                return null;
            } else {
                return this.value;
            }
        }
    }
}
