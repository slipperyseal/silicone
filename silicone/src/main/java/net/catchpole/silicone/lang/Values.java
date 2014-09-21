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

public class Values {
    public String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length*2);
        for (byte b : bytes) {
            sb.append(hexChar((b&0xff)>>4));
            sb.append(hexChar(b & 0x0f));
        }
        return sb.toString();
    }

    private char hexChar(int c) {
        return (char)(c <= 9 ? (c + '0') : (c - 10 + 'a'));
    }
}
