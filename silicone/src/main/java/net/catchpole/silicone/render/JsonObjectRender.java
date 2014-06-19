package net.catchpole.silicone.render;

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

import net.catchpole.silicone.lang.Throw;
import net.catchpole.silicone.servlet.Backing;
import net.catchpole.silicone.servlet.Path;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class JsonObjectRender implements Render {
    private final Object object;
    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonObjectRender(Object object) {
        this.object = object;
    }

    public void render(OutputStream os, Path path, Backing backing) throws IOException {
        try {
            objectMapper.writeValue(os, object);
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public String getContentType() {
        return "application/json";
    }
}
