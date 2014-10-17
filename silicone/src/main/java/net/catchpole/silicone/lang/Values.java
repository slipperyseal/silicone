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
            sb.append(toHexChar((b & 0xff) >> 4));
            sb.append(toHexChar(b & 0x0f));
        }
        return sb.toString();
    }

    public String toHexString(int value) {
        char[] chars = new char[8];
        for (int x=0;x<8;x++) {
            chars[x] = toHexChar((value >> ((7 - x) * 4) & 0x0f));
        }
        return new String(chars);
    }

    public String toHexString(long value) {
        char[] chars = new char[16];
        for (int x=0;x<16;x++) {
            chars[x] = toHexChar((int)(value >> ((15 - x) * 4) & 0x0f));
        }
        return new String(chars);
    }

    public char toHexChar(int c) {
        return (char)(c <= 9 ? (c + '0') : (c - 10 + 'a'));
    }

    public byte[] hexToBytes(String value) {
        int len = value.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4) + Character.digit(value.charAt(i+1), 16));
        }
        return data;
    }
}
