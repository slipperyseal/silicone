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

import java.util.ArrayList;
import java.util.List;

public class RelativePathOffset {
    private List<String> list = new ArrayList<String>();

    public RelativePathOffset() {
        for (int x=0;x<20;x++) {
            list.add(renderPath(x));
        }
    }

    public String getOffset(int depth) {
        return depth <= list.size() ? list.get(depth) : renderPath(depth);
    }

    private String renderPath(int offset) {
        StringBuilder sb = new StringBuilder();
        for (int x=0;x<offset;x++) {
            sb.append("../");
        }
        return sb.toString();
    }
}
