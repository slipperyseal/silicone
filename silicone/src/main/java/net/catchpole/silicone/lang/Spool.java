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
    private final byte[] spoolBuffer;

    public Spool() {
        this(4096);
    }

    public Spool(int bufferSize) {
        this.spoolBuffer = new byte[bufferSize];
    }

    public Spool(byte[] spoolBuffer) {
        this.spoolBuffer = spoolBuffer;
    }

    public void spool(InputStream is, OutputStream os) throws IOException {
        int l;
        while ((l = is.read(spoolBuffer, 0, spoolBuffer.length)) != -1) {
            os.write(spoolBuffer, 0, l);
        }
    }

    public byte[] spoolToByteArray(InputStream is) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(spoolBuffer.length);
        this.spool(is, baos);
        return baos.toByteArray();
    }

    public void spoolLimited(InputStream is, OutputStream os, long limit) throws IOException {
        int l;
        limit++; // spooling exactly limit allowed. fail at limit + 1
        while ((l = is.read(spoolBuffer, 0, spoolBuffer.length < limit ? spoolBuffer.length : (int)limit)) != -1) {
            if ((limit -= l) <= 0) {
                throw new IOException("spooling limit reached");
            }
            os.write(spoolBuffer, 0, l);
        }
    }
}
