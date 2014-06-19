package net.catchpole.silicone.action;

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
import java.util.Iterator;
import java.util.Map;

public class MapArtefacts implements Artefacts {
    private final Map<String,Object> map = new HashMap<String, Object>();

    public MapArtefacts() {
    }

    public void set(Object object) {
        if (object instanceof Class) {
            throw new IllegalArgumentException();
        }
        map.put(object.getClass().getSimpleName(), object);
    }

    public <T extends Object> T get(Class<T> clazz) {
        return (T)map.get(clazz.getSimpleName());
    }

    public void remove(Class clazz) {
        map.remove(clazz.getSimpleName());
    }

    @Override
    public Iterator iterator() {
        return map.values().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return map.equals(((MapArtefacts)o).map);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    public String toString() {
        return map.keySet().toString();
    }
}
