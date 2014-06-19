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

import net.catchpole.silicone.session.MemorySessionPersist;
import net.catchpole.silicone.SessionPersist;
import net.catchpole.silicone.SiliconeConfig;
import net.catchpole.silicone.action.Action;
import net.catchpole.silicone.action.Actions;
import net.catchpole.silicone.action.annotation.Endpoint;
import net.catchpole.silicone.lang.reflect.Reflection;
import net.catchpole.silicone.render.*;
import net.catchpole.silicone.resource.ServletContextResourceSource;

import javax.servlet.ServletConfig;

public class SiliconeConfigBridge implements SiliconeConfig {
    private final RenderSelector renderSelector = new RenderSelector();
    private final Actions actions;
    private final Reflection reflection = new Reflection();
    private SessionPersist sessionPersist;

    public SiliconeConfigBridge(ServletConfig servletConfig, Actions actions) {
        this.actions = actions;

        Render templateRender = new TemplateRender(
                new ServletContextResourceSource(servletConfig.getServletContext()),
                "root.xsl");
        renderSelector.add(new Path(), templateRender);
    }

    public void addRender(String path, Render render) {
        renderSelector.add(new Path(path), render);
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public void setStatelessAction(Action statelessAction) {
        actions.setStatelessAction(statelessAction);
    }

    public void setSessionAction(Action sessionAction) {
        actions.setSessionAction(sessionAction);
    }

    public void setGlobalAction(Action globalAction) {
        actions.setGlobalAction(globalAction);
    }

    public RenderSelector getRenderSelector() {
        return renderSelector;
    }

    public void setSessionPersist(SessionPersist sessionPersist) {
        this.sessionPersist = sessionPersist;
    }

    public SessionPersist getSessionPersist() {
        if (sessionPersist == null) {
            sessionPersist = new MemorySessionPersist();
        }
        return sessionPersist;
    }

    public void registerGlobalEndpoint(Object artefact) {
        String service = artefact.getClass().getSimpleName().toLowerCase();
        this.addRender("service/json/" + service, new JsonObjectRender(artefact));
    }

    public void registerArtefact(Class artefactClass) {
        if (reflection.findAnnotation(artefactClass, Endpoint.class) != null) {
            String service = artefactClass.getSimpleName().toLowerCase();
            this.addRender("service/xml/" + service, new XmlRender(artefactClass));
            this.addRender("service/json/" + service, new JsonRender(artefactClass));
        }
    }
}
