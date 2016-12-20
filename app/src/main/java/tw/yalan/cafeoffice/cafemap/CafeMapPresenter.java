package tw.yalan.cafeoffice.cafemap;

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

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.grasea.grandroid.api.Callback;
import com.grasea.grandroid.api.RemoteProxy;
import com.grasea.grandroid.api.RequestFail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import tw.yalan.cafeoffice.Config;
import tw.yalan.cafeoffice.api.API;
import tw.yalan.cafeoffice.common.BasePresenter;
import tw.yalan.cafeoffice.data.ModelManager;
import tw.yalan.cafeoffice.model.Cafe;
import tw.yalan.cafeoffice.utils.MapUtils;

/**
 * Created by Alan Ding on 2016/12/19.
 */
public class CafeMapPresenter extends BasePresenter<CafeMapActivity> {
    Location mCurrentLocation;
    String city = "";

    @Override
    public void onActivityCreate(Bundle extras) {

    }

    @Override
    public void onActivityResume() {

    }

    @Override
    public void onActivityPause() {

    }

    @Override
    public void onActivityDestroy() {

    }

    public void getCafesData(Location mCurrentLocation, int city) {
        this.mCurrentLocation = mCurrentLocation;
        API api = RemoteProxy.reflect(API.class, this);
        switch (city) {
            case 0:
                api.requestTaipeiCafes();
                break;
            case 1:
                api.requestHsinchuCafes();
                break;
            case 2:
                api.requestTaichungCafes();
                break;
            case 3:
                api.requestKaohsiungCafes();
                break;
        }
    }

    //
    @Callback("requestTaipeiCafes")
    public void onTaipeiCafesResponse(List<Cafe> cafeList) {
        Config.loge("onTaipeiCafesResponse");
        handleCallback("taipei", cafeList);
    }

    @Callback("requestHsinchuCafes")
    public void onHsinchuCafesResponse(List<Cafe> cafeList) {
        handleCallback("hsinchu", cafeList);
    }

    @Callback("requestTaichungCafes")
    public void onTaichungCafesResponse(List<Cafe> cafeList) {
        handleCallback("taichung", cafeList);
    }

    @Callback("requestKaohsiungCafes")
    public void onKaohsiungCafesResponse(List<Cafe> cafeList) {
        handleCallback("kaohsiung", cafeList);
    }

    @RequestFail
    public void onRequestFailed(String method, Throwable t) {
        Config.loge(t);
        getContract().networkNotFound();
    }

    public void handleCallback(String city, List<Cafe> cafes) {
        this.city = city;
        Config.loge("cafe count:" + cafes.size());
        ArrayList cafeList = new ArrayList(cafes);
        ModelManager.get().getUserModel().putCafeList(cafeList);

        getContract().updateCafeList(city, cafeList);
    }

    public void onCameraIdle() {
        ArrayList<Cafe> cafeList = ModelManager.get().getUserModel().getCafeList();
        if (cafeList != null) {
            getContract().updateCafeList(city, cafeList);
        }
    }

    public void searchNearCafe(Location mCurrentLocation) {
        ArrayList<Cafe> cafeList = ModelManager.get().getUserModel().getCafeList();
        Cafe neaiestCafe = null;
        Integer neaiestDistance = -1;
        LatLng myLocation = MapUtils.parseToLatLng(mCurrentLocation);
        for (Cafe cafe : cafeList) {
            String[] distance = MapUtils.getDistance(myLocation, new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude())));
            if (neaiestDistance == -1) {
                neaiestCafe = cafe;
                neaiestDistance = Integer.valueOf(distance[0]);
            } else {
                neaiestDistance = Math.min(Integer.valueOf(distance[0]), neaiestDistance);
                if(Integer.valueOf(distance[0]) == neaiestDistance){
                    neaiestCafe = cafe;
                }
            }
        }
        getContract().showNearMyCafe(neaiestCafe);
    }
}
