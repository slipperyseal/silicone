package net.catchpole.silicone;

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

import net.catchpole.silicone.action.Action;
import net.catchpole.silicone.action.Endpoint;
import net.catchpole.silicone.render.Render;
import net.catchpole.silicone.session.SessionHashPersist;

public interface SiliconeConfig {
    public void setSessionHashPersist(SessionHashPersist sessionHashPersist);

    public void addAction(Action action);

    public void setStatelessAction(Action statelessAction);

    public void setSessionAction(Action sessionAction);

    public void setGlobalAction(Action globalAction);

    public void addRender(String path, Render render);

    public void registerArtefact(Class clazz);

    public void registerGlobalEndpoint(Endpoint endpoint);
}
