package tw.yalan.cafeoffice.model;

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

/**
 * Created by Alan Ding on 2016/12/19.
 */
public class Cafe {

    /**
     * id : 0022fc3b-598f-4bb5-bb69-1b7d1b9b5202
     * name : The Kaffa Lovers
     * city : taipei
     * wifi : 5
     * seat : 5
     * quiet : 4
     * tasty : 5
     * cheap : 4
     * music : 4
     * url : https://www.facebook.com/thekaffalovers/?fref=ts
     * address : 台北市中正區金山北路3號
     * latitude : 25.04435400
     * longitude : 121.53045590
     */

    private String id;
    private String name;
    private String city;
    private double wifi;
    private double seat;
    private double quiet;
    private double tasty;
    private double cheap;
    private double music;
    private String url;
    private String address;
    private String latitude;
    private String longitude;

    private String distance;
    public Cafe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getWifi() {
        return wifi;
    }

    public void setWifi(double wifi) {
        this.wifi = wifi;
    }

    public double getSeat() {
        return seat;
    }

    public void setSeat(double seat) {
        this.seat = seat;
    }

    public double getQuiet() {
        return quiet;
    }

    public void setQuiet(double quiet) {
        this.quiet = quiet;
    }

    public double getTasty() {
        return tasty;
    }

    public void setTasty(double tasty) {
        this.tasty = tasty;
    }

    public double getCheap() {
        return cheap;
    }

    public void setCheap(double cheap) {
        this.cheap = cheap;
    }

    public double getMusic() {
        return music;
    }

    public void setMusic(double music) {
        this.music = music;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

