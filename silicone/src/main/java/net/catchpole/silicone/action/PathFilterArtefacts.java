package net.catchpole.silicone.action;

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

import net.catchpole.silicone.action.annotation.OnPath;
import net.catchpole.silicone.lang.reflect.Reflection;
import net.catchpole.silicone.servlet.Path;

public class PathFilterArtefacts extends MapArtefacts {
    public PathFilterArtefacts(Artefacts artefacts, Path path) {
        for (Object object : artefacts) {
            if (isValidForPath(path, object)) {
                super.set(object);
            }
        }
    }

    private boolean isValidForPath(Path path, Object object) {
        Reflection reflection = new Reflection();
        OnPath onPath = reflection.findAnnotation(object.getClass(), OnPath.class);
        if (onPath != null) {
            Path annotationPath = new Path(onPath.path());
            if (!annotationPath.equals(path)) {
                // not for this path. awkward.
                return false;
            }
        }
        return true;
    }
}
