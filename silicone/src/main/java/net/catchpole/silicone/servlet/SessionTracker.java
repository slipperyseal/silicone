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

import net.catchpole.silicone.SessionPersist;
import net.catchpole.silicone.action.Actions;
import net.catchpole.silicone.action.Artefacts;
import net.catchpole.silicone.action.MapArtefacts;
import net.catchpole.silicone.state.SessionCookieGenerator;
import net.catchpole.silicone.state.UidSource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionTracker {
    private static final String COOKIENAME = "SILICONE";
    private final SessionPersist sessionPersist;
    private final Actions actions;
    private final UidSource uidSource = new SessionCookieGenerator();

    public SessionTracker(SessionPersist sessionPersist, Actions actions) {
        this.sessionPersist = sessionPersist;
        this.actions = actions;
    }

    public RequestScope startRequestScope(HttpServletRequest httpServletRequest,
                                          HttpServletResponse httpServletResponse, Actions actions) {
        final RequestScope requestScope = new RequestScope();
        String cookie = findTrackingCookie(httpServletRequest);

        String value = sessionPersist.getSession(cookie);
        if (value == null) {
            cookie = null;
            removeTrackingCookie(httpServletRequest, httpServletResponse);
        }

        requestScope.setCookie(cookie);

        Artefacts artefacts = new MapArtefacts();
        actions.getGlobalAction().perform(null, artefacts);
        if (cookie != null) {
            actions.getSessionAction().perform(null, artefacts);
        } else {
            actions.getStatelessAction().perform(null, artefacts);
        }
        requestScope.setArtefacts(artefacts);

        Class sessionClass = actions.getSessionClass();
        requestScope.hadSessionClass(sessionClass != null && artefacts.get(sessionClass) != null);

        return requestScope;
    }

    public void finishRequestScope(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, RequestScope requestScope) {
        Artefacts artefacts = requestScope.getArtefacts();
        String cookie = requestScope.getCookie();
        Class sessionClass = actions.getSessionClass();
        Object session = sessionClass != null ? artefacts.get(sessionClass) : null;

        if (session != null) {
            if (!requestScope.hadSessionClass()) {
                // session is new
                artefacts = new MapArtefacts();
                actions.getGlobalAction().perform(null, artefacts);
                actions.getSessionAction().perform(null, artefacts);
                requestScope.setArtefacts(artefacts);
            }

            if (cookie == null) {
                cookie = uidSource.createUID();
                requestScope.setCookie(cookie);
                sessionPersist.setSession(cookie, "value");
                addTrackingCookie(httpServletResponse, cookie);
            }
        } else {
            if (cookie != null) {
                removeTrackingCookie(httpServletRequest, httpServletResponse);
                requestScope.setCookie(null);
                sessionPersist.delete(cookie);
            }

            if (requestScope.hadSessionClass()) {
                // session just closed
                artefacts = new MapArtefacts();
                actions.getGlobalAction().perform(null, artefacts);
                actions.getStatelessAction().perform(null, artefacts);
                requestScope.setArtefacts(artefacts);
            }
        }
    }

    private void addTrackingCookie(HttpServletResponse httpServletResponse, String sessionToken) {
        Cookie cookie = new Cookie(COOKIENAME, sessionToken);
        cookie.setMaxAge(60*60*24*365);
        httpServletResponse.addCookie(cookie);
    }

    private void removeTrackingCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIENAME)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    httpServletResponse.addCookie(cookie);
                }
            }
        }
    }

    public String findTrackingCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIENAME)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
