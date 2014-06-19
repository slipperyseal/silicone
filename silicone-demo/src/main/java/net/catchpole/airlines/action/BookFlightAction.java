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

import net.catchpole.airlines.artefact.BookFlight;
import net.catchpole.airlines.artefact.Flights;
import net.catchpole.airlines.model.Flight;
import net.catchpole.silicone.action.Action;
import net.catchpole.silicone.action.Artefacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookFlightAction implements Action<BookFlight> {
    private Flights flights;
    private Map<String,List<Flight>> bookingsMap;

    public BookFlightAction(Flights flights, Map<String,List<Flight>> bookingsMap) {
        this.flights = flights;
        this.bookingsMap = bookingsMap;
    }

    @Override
    public void perform(BookFlight bookFlight, Artefacts artefacts) {
        String flightNumber = bookFlight.getFlight();
        if (flightNumber == null) {
            return;
        }

        flightNumber = flightNumber.toUpperCase();

        Flight flight = flights.getFlights().get(flightNumber);
        if (flight == null) {
            throw new IllegalArgumentException("Unable to find flight: " + flightNumber);
        }
        List<Flight> flightList = bookingsMap.get("all");
        if (flightList == null) {
            flightList = new ArrayList<Flight>();
            bookingsMap.put("all", flightList);
        }
        flightList.add(flight);

        bookFlight.setFlight(null);
    }
}
