package net.catchpole.airlines.service;

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

import net.catchpole.airlines.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String,User> users = new HashMap<String, User>();

    public UserService() {
        User user = new User();
        user.setName("test");
        user.setPassword("password");
        users.put(user.getName(), user);
    }

    public User createUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        users.put(name, user);

        return user;
    }

    public User createSession(String name, String password) {
        User user = users.get(name);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("BZZZ! Wrong username or password!");
        }
        return user;
    }
}
