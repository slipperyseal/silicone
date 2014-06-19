package net.catchpole.airlines.model;

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

import java.util.ArrayList;
import java.util.List;

public class FlightBuilder {
    private String[] destinations = new String[] { "BNE", "SYD", "LAX", "SFO" };

    public List<Flight> build() {
        List<Flight> flights = new ArrayList<Flight>();
        for (int x=5;x<22;x+=2) {
            flights.add(new Flight(
                    getFlight(x),
                    getGate(x),
                    getTime(x, ((x*5) & 3) * 15),
                    "MEL", destinations[ (x/2) & 3 ]
            ));
        }
        return flights;
    }

    private String getGate(int base) {
        return "" + (((base * 22) & 0xf) + 1);
    }

    private String getFlight(int base) {
        return "SA" + ((base * 12) + 100);
    }

    private String getTime(int hour, int mins) {
        return (hour < 10 ? "0" : "") + hour + ":" + (mins < 10 ? "0" : "") + mins;
    }
}
