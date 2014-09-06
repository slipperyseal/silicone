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

import net.catchpole.silicone.SessionPersist;
import net.catchpole.silicone.lang.TimeCache;

public class CacheSessionPersist implements SessionPersist {
    private final TimeCache<String,String> timeCache;
    private final SessionPersist sessionPersist;

    public CacheSessionPersist(SessionPersist sessionPersist, int milliseconds) {
        this.timeCache = new TimeCache<String, String>(milliseconds);
        this.sessionPersist = sessionPersist;
    }

    @Override
    public synchronized String getSession(String key) {
        String value = timeCache.get(key);
        if (value == null) {
            value = sessionPersist.getSession(key);
            if (value != null) {
                timeCache.put(key, value);
            }
        }
        return value;
    }

    @Override
    public synchronized void setSession(String key, String value) {
        timeCache.put(key, value);
        sessionPersist.setSession(key, value);
    }

    @Override
    public synchronized void delete(String key) {
        timeCache.remove(key);
        sessionPersist.delete(key);
    }
}
