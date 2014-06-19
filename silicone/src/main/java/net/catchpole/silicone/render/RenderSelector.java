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

import net.catchpole.silicone.servlet.Path;

import java.util.HashMap;
import java.util.Map;

public class RenderSelector {
    private Map<Path,Render> renderMap = new HashMap<Path, Render>();

    public RenderSelector() {
    }

    public void add(Path path, Render render) {
        renderMap.put(path, render);
    }

    public Render find(Path path) {
        Render render = renderMap.get(path);
        if (render != null) {
            return render;
        }
// todo: disable path search
//        Path findPath = new Path(path);
//        while (findPath.removeLast()) {
//            render = renderMap.get(findPath);
//            if (render != null) {
//                return render;
//            }
//        }
        return null;
    }

    public Path findPath(Path path) {
        Render render = renderMap.get(path);
        if (render != null) {
            return path;
        }
        Path findPath = new Path(path);
        while (findPath.removeLast()) {
            render = renderMap.get(findPath);
            if (render != null) {
                return findPath;
            }
        }
        return null;
    }
}
