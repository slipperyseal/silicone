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

import net.catchpole.airlines.artefact.CheckIn;
import net.catchpole.silicone.action.Action;
import net.catchpole.silicone.action.Artefacts;
import net.catchpole.silicone.action.annotation.AllowGetRequest;

@AllowGetRequest
public class CheckInAction implements Action<CheckIn> {
    public CheckInAction() {
    }

    @Override
    public void perform(CheckIn checkIn, Artefacts artefacts) {
        String checkInKey = checkIn.getCheckin();
        // check me in here please
    }
}
