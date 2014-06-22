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

import net.catchpole.silicone.action.Artefacts;

public class Backing {
    private Artefacts artefacts;
    private String build;
    private String status;
    private String notice;
    private String pathOffset;
    private Path path;
    private String auth;
    private Object requestPayload;

    public Artefacts getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(Artefacts artefacts) {
        this.artefacts = artefacts;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getPathOffset() {
        return pathOffset;
    }

    public void setPathOffset(String pathOffset) {
        this.pathOffset = pathOffset;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Object getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(Object requestPayload) {
        this.requestPayload = requestPayload;
    }
}
