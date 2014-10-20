package net.catchpole.airlines;

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

import net.catchpole.airlines.action.*;
import net.catchpole.airlines.artefact.*;
import net.catchpole.airlines.model.Flight;
import net.catchpole.airlines.model.FlightBuilder;
import net.catchpole.airlines.service.UserService;
import net.catchpole.silicone.SiliconeConfig;
import net.catchpole.silicone.SiliconeSetup;
import net.catchpole.silicone.action.Action;
import net.catchpole.silicone.action.Artefacts;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AirlinesSetup implements SiliconeSetup {
    private UserService userService = new UserService();
    private Flights flights = new Flights(new FlightBuilder().build());
    private Map<String,List<Flight>> bookingsMap = new TreeMap<String,List<Flight>>();

    public void setupSilicon(SiliconeConfig siliconeConfig) {
        siliconeConfig.addAction(new CreateUserAction(this.userService));
        siliconeConfig.addAction(new LoginAction(this.userService));
        siliconeConfig.addAction(new LogoutAction());
        siliconeConfig.addAction(new CheckInAction());
        siliconeConfig.addAction(new BookFlightAction(this.flights, this.bookingsMap));
        siliconeConfig.registerArtefact(Flights.class);

        siliconeConfig.setGlobalAction(new Action() {
            public void perform(Object object, Artefacts artefacts) {
                artefacts.set(flights);
                artefacts.set(new CheckIn());
            }
        });

        siliconeConfig.setStatelessAction(new Action() {
            public void perform(Object object, Artefacts artefacts) {
                artefacts.set(new Login());
                artefacts.set(new CreateUser());
            }
        });

        siliconeConfig.setSessionAction(new Action() {
            public void perform(Object object, Artefacts artefacts) {
                artefacts.set(new BookFlight());
                artefacts.set(new Logout());
                artefacts.set(new Bookings(bookingsMap));
            }
        });
    }
}
