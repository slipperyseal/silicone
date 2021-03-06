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

import net.catchpole.silicone.SiliconeSetup;
import net.catchpole.silicone.action.Actions;
import net.catchpole.silicone.action.HttpRequestDetails;
import net.catchpole.silicone.action.PathFilterArtefacts;
import net.catchpole.silicone.lang.BuildNumber;
import net.catchpole.silicone.lang.Throw;
import net.catchpole.silicone.lang.Uid;
import net.catchpole.silicone.render.JsonEndpointRender;
import net.catchpole.silicone.render.Render;
import net.catchpole.silicone.render.RenderSelector;
import net.catchpole.silicone.session.MemorySessionHashPersist;
import net.catchpole.silicone.session.MemoryValidatedCookies;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SiliconeServlet extends HttpServlet {
    private final BuildNumber buildNumber = new BuildNumber();
    private final RelativePathOffset relativePathOffset = new RelativePathOffset();
    private final Actions actions = new Actions();
    private SiliconeConfigBridge siliconeConfig;
    private RenderSelector renderSelector;
    private SessionTracker sessionTracker;

    public void init(ServletConfig servletConfig) throws ServletException {
        this.siliconeConfig = new SiliconeConfigBridge(servletConfig, actions);

        String setupClassName = servletConfig.getInitParameter("silicone-setup");
        if (setupClassName == null) {
            throw new IllegalArgumentException("no servlet parameter: silicon-setup");
        }
        try {
            SiliconeSetup siliconeSetup = (SiliconeSetup)Class.forName(setupClassName).newInstance();
            siliconeSetup.setupSilicon(siliconeConfig);

            this.sessionTracker = new SessionTracker(new MemoryValidatedCookies(), new MemorySessionHashPersist(), actions);
            this.renderSelector = siliconeConfig.getRenderSelector();
        } catch (Exception e) {
            throw Throw.unchecked(e);
        }
    }

    public void destroy() {
    }

    public void doPost(final HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            handle(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.sendError(500);
        }
    }

    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            handle(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            httpServletResponse.sendError(500);
        }
    }

    private void handle(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws Exception {
        final Backing backing = new Backing();
        backing.setBuild(buildNumber.getBuild());
        final Path path = new Path(httpServletRequest);
        backing.setPath(path);

        // redirect /webappname to /webappname/
        if (path.getDepth() == 0 && !path.hasTrailingSlash()) {
            httpServletResponse.sendRedirect(httpServletRequest.getRequestURI() + "/");
            return;
        }

        backing.setPathOffset(this.relativePathOffset.getOffset(path.getDepth()));

        Render render = renderSelector.find(path);
        if (render == null) {
            render = renderSelector.find(new Path());
            backing.setStatus("404");
            httpServletResponse.setStatus(404);
        } else {
            backing.setStatus("200");

            if (render instanceof JsonEndpointRender) {
                InputLoader inputLoader = new InputLoader(httpServletRequest);
                backing.setRequestPayload(inputLoader.parseJson(siliconeConfig.getEndpointInputType(path.get(2))));
            }

            RequestScope requestScope = sessionTracker.startRequestScope(httpServletRequest, httpServletResponse, actions);

            if (requestScope.hadSessionClass()) {
                String cookie = sessionTracker.findTrackingCookie(httpServletRequest);
                if (cookie != null) {
                    Uid uid = new Uid("formname".getBytes()).getHash(cookie.getBytes());
                    backing.setAuth(uid.toString());
                }
            }

            String error = actions.perform(httpServletRequest, requestScope.getArtefacts());
            if (error != null) {
                backing.setNotice(error);
            }

            sessionTracker.finishRequestScope(httpServletRequest, httpServletResponse, requestScope);
            backing.setArtefacts(new PathFilterArtefacts(requestScope.getArtefacts(), path));
        }

        httpServletResponse.setContentType(render.getContentType());
        try {
            render.render(httpServletResponse.getOutputStream(), backing, new HttpRequestDetails(httpServletRequest));
        } catch (IllegalArgumentException iae) {
            httpServletResponse.setStatus(403);
        }
    }

    protected long getLastModified(HttpServletRequest req) {
        return -1;
    }
}
