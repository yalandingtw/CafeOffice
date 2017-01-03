package tw.yalan.cafeoffice.common;

/**
 * Copyright (C) 2016 Alan Ding
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Alan Ding on 2016/12/31.
 */
public enum City {
    TAIPEI(25.0437847, 121.526903), TAICHUNG(24.1375006, 120.6844106), HSINCHU(24.8015844, 120.9694678), KAOHSIUNG(22.6384266, 120.3038419);
    double lat;
    double lng;

    City(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
