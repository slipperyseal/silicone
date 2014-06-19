package net.catchpole.airlines.artefact;

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

import net.catchpole.airlines.model.Flight;
import net.catchpole.silicone.action.annotation.Endpoint;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Endpoint
public class Flights {
    private Map<String,Flight> flights = new TreeMap<String, Flight>();

    public Flights(List<Flight> flightList) {
        for (Flight flight : flightList) {
            this.flights.put(flight.getNumber(), flight);
        }
    }

    public Map<String,Flight> getFlights() {
        return flights;
    }
}
