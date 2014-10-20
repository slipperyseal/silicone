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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class MachineInfo {
    private String server;
    private String hexAddress = "00000000";

    public MachineInfo() {
        try {
            StringBuilder sb = new StringBuilder();

            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface networkInterface = e.nextElement();
                if (!networkInterface.isLoopback()) {
                    Enumeration<InetAddress> ei = networkInterface.getInetAddresses();
                    while (ei.hasMoreElements()) {
                        InetAddress inetAddress = ei.nextElement();
                        String address = inetAddress.getHostAddress();
                        if (address.indexOf(':') == -1) {
                            if (sb.length() != 0) {
                                sb.append('/');
                            }
                            sb.append(address);
                            this.hexAddress = new Values().toHexString(inetAddress.getAddress());
                        }
                    }
                }
            }
            if (sb.length() == 0) {
                sb.append("no devices");
            }
            server = sb.toString();
        } catch (Throwable t) {
            server = "unknown";
        }
    }

    public String getServer() {
        return server;
    }

    public String getHexAddress() {
        return hexAddress;
    }
}
