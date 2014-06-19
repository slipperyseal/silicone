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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class Spool {
    private Spool() {
    }

    public static void spool(InputStream is, OutputStream os) throws IOException {
        spool(is, os, 4096);
    }

    public static void spool(InputStream is, OutputStream os, int spoolSize) throws IOException {
        spool(is, os, new byte[spoolSize]);
    }

    public static void spool(InputStream is, OutputStream os, byte[] spoolBuffer) throws IOException {
        int l;
        while ((l = is.read(spoolBuffer, 0, spoolBuffer.length)) != -1) {
            os.write(spoolBuffer, 0, l);
        }
    }

    public static byte[] spoolToByteArray(InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
        spool(is, baos);
        return baos.toByteArray();
    }
}
