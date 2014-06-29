package net.catchpole.silicone.servlet;

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

import net.catchpole.silicone.lang.Spool;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class InputLoader {
    private final HttpServletRequest httpServletRequest;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public InputLoader(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public Object parseJson(Class type) throws IOException {
        InputStream inputStream = getInputStreamBuffered(64*1024);
        return inputStream == null ? null : objectMapper.readValue(inputStream, type);
    }

    private InputStream getInputStreamBuffered(long limit) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        new Spool().spoolLimited(getInputStreamActual(), byteArrayOutputStream, limit);
        byte[] data = byteArrayOutputStream.toByteArray();
        return data.length == 0 ? null : new ByteArrayInputStream(data);
    }

    private InputStream getInputStreamActual() throws IOException {
        if (ServletFileUpload.isMultipartContent(httpServletRequest)) {
            try {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator fileItemIterator = upload.getItemIterator(httpServletRequest);
                while (fileItemIterator.hasNext()) {
                    FileItemStream item = fileItemIterator.next();
                    if (!item.isFormField()) {
                        return item.openStream();
                    }
                }
            } catch (FileUploadException fe) {
                throw new RuntimeException(fe);
            }
        } else {
            return httpServletRequest.getInputStream();
        }
        return null;
    }
}
