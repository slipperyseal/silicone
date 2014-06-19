package net.catchpole.silicone.async;

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

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AsyncListener<V> {
    private long millisTimeout;
    private LinkedBlockingQueue<V> queue = new LinkedBlockingQueue<V>();

    public AsyncListener(long millisTimeout) {
        this.millisTimeout = millisTimeout;
    }

    public V waitForObject() {
        try {
            return queue.poll(millisTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException i) {
            throw new RuntimeException(i);
        }
    }

    public void addObject(V value) {
        queue.add(value);
    }
}
