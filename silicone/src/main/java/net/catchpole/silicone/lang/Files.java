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

import java.io.*;

public final class Files {
    private Files() {
    }

    public static byte[] loadFile(File file) throws IOException {
        byte data[] = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        try {
            new DataInputStream(fis).readFully(data);
        } finally {
            fis.close();
        }
        return data;
    }

    public static void saveFile(File file, byte[] bytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(bytes);
        } finally {
            fos.close();
        }
    }

    public static void trimFile(File file, long length) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        try {
            raf.setLength(length);
        } finally {
            raf.close();
        }
    }

    /**
     * Compares two files by reading every byte of each file using buffers.
     *
     * @param f1
     * @param f2
     * @return
     * @throws IOException
     */
    public static boolean fileEquals(File f1, File f2) throws IOException {
        if (f1 == null || f2 == null || f1.length() != f2.length()) {
            return false;
        }
        // use actual size of files if less than 512
        // use small buffer, so that non-equal files dont need to load so much at start
        int bufferlen = (int) (f1.length() < 512 ? f1.length() : 512);
        byte[] test1 = new byte[bufferlen];
        byte[] test2 = new byte[bufferlen];
        long remain = f1.length();
        if (remain == 0) {
            return true;
        }

        DataInputStream is1 = new DataInputStream(new FileInputStream(f1));
        try {
            DataInputStream is2 = new DataInputStream(new FileInputStream(f2));
            try {
                while (remain > 0) {
                    // bytes to read - buffer length or any remainder
                    int len = (remain > test1.length ? test1.length : (int) remain);
                    is1.readFully(test1, 0, len);
                    is2.readFully(test2, 0, len);
                    if (!java.util.Arrays.equals(test1, test2)) {
                        return false;
                    }
                    remain -= len;
                }
            } finally {
                is2.close();
            }
        } finally {
            is1.close();
        }
        return true;
    }

    public static byte[] loadResource(Class clazz, String resource) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        spoolResource(clazz, resource, baos);
        return baos.toByteArray();
    }

    public static void spoolResource(Class clazz, String resource, OutputStream os) throws IOException {
        InputStream is = clazz.getResourceAsStream(resource);
        try {
            new Spool().spool(is, os);
        } finally {
            is.close();
        }
    }
}
