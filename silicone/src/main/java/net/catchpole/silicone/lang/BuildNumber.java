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
import java.io.InputStream;

public class BuildNumber {
    private String build;

    public BuildNumber() {
        InputStream inputStream = this.getClass().getResourceAsStream("/build-number");
        if (inputStream == null) {
            build = "unknown";
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Spool.spool(inputStream, baos);
            try {
                build = baos.toString("utf-8").trim();
            } finally {
                inputStream.close();
            }
        } catch (Exception e) {
            build = "unknown";
        }
    }

    public String getBuild() {
        return build;
    }

}
