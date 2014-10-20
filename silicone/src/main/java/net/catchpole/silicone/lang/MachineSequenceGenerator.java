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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MachineSequenceGenerator {
    private static Sequence sequence = new Sequence();

    private Values values = new Values();
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    private String server;

    public MachineSequenceGenerator(MachineInfo machineInfo) {
        this.server = machineInfo.getHexAddress();
    }

    public synchronized String getServerUid() {
        StringBuilder sb = new StringBuilder(20);
        String time = dateFormatter.format(new Date());
        sb.append(time);
        sb.append(values.toHexString(sequence.next(time)));
        sb.append(server);
        return sb.toString();
    }

    static class Sequence {
        private String lastTime;
        private int increment;

        public synchronized int next(String time) {
            if (time.equals(lastTime)) {
                increment++;
            } else {
                lastTime = time;
                increment=0;
            }
            return increment;
        }
    }
}