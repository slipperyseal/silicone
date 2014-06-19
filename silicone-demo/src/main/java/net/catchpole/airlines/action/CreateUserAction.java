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

import net.catchpole.airlines.artefact.CreateUser;
import net.catchpole.airlines.artefact.Logout;
import net.catchpole.airlines.model.User;
import net.catchpole.airlines.service.UserService;
import net.catchpole.silicone.action.Action;
import net.catchpole.silicone.action.Artefacts;

public class CreateUserAction implements Action<CreateUser> {
    private UserService userService;

    public CreateUserAction(UserService userService) {
        this.userService = userService;
    }

    public void perform(CreateUser createUserBean, Artefacts artefacts) {
        if (createUserBean.getName() == null || createUserBean.getName().length() == 0 ||
                createUserBean.getPassword() == null || createUserBean.getPassword().length() == 0) {
            throw new IllegalArgumentException("Some of them boxes empty dude.");
        }

        this.userService.createUser(createUserBean.getName(), createUserBean.getPassword());

        User user = userService.createSession(createUserBean.getName(), createUserBean.getPassword());
        artefacts.set(new Logout());
    }
}
