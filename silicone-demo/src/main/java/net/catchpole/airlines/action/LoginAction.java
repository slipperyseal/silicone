package net.catchpole.airlines.action;

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

import net.catchpole.airlines.artefact.Login;
import net.catchpole.airlines.artefact.Logout;
import net.catchpole.airlines.model.User;
import net.catchpole.airlines.service.UserService;
import net.catchpole.silicone.action.Action;
import net.catchpole.silicone.action.Artefacts;

public class LoginAction implements Action<Login> {
    private UserService userService;

    public LoginAction(UserService userService) {
        this.userService = userService;
    }

    public void perform(Login login, Artefacts artefacts) {
        if (login.getName() == null || login.getName().length() == 0 ||
                login.getPassword() == null || login.getPassword().length() == 0) {
            throw new IllegalArgumentException("Some of them boxes empty dude.");
        }

        User user = userService.createSession(login.getName(), login.getPassword());
        if (user == null) {
            throw new IllegalArgumentException("Who are you?");
        }

        artefacts.set(new Logout());
    }
}
