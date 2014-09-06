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

import net.catchpole.silicone.servlet.Path;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestDetails implements RequestDetails {
    private HttpServletRequest httpServletRequest;

    public HttpRequestDetails(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public String getOrigin() {
        return httpServletRequest.getRemoteAddr();
    }

    @Override
    public Path getPath() {
        return new Path(httpServletRequest);
    }

    @Override
    public String getHeader(String name) {
        return httpServletRequest.getHeader(name);
    }
}
