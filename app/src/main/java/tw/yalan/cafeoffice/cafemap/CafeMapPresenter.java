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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import tw.yalan.cafeoffice.Config;
import tw.yalan.cafeoffice.api.API;
import tw.yalan.cafeoffice.api.GoogleGeoAPI;
import tw.yalan.cafeoffice.common.BasePresenter;
import tw.yalan.cafeoffice.common.City;
import tw.yalan.cafeoffice.common.FilterType;
import tw.yalan.cafeoffice.data.ModelManager;
import tw.yalan.cafeoffice.model.Cafe;
import tw.yalan.cafeoffice.model.GoogleGeoDetail;
import tw.yalan.cafeoffice.utils.MapUtils;

/**
 * Created by Alan Ding on 2016/12/19.
 */
public class CafeMapPresenter extends BasePresenter<CafeMapActivity> {
    Location mCurrentLocation;
    String city = "";
    FilterType mType = FilterType.ALL;
    GoogleGeoAPI geoAPI;
    API api;
    String currentCityShortName = "";
    String currentFilterLevel = "3.5";

    @Override
    public void onActivityCreate(Bundle extras) {
        initDefaultValues();
        getContract().initViews();
    }

    private void initDefaultValues() {
        String filterLevel = ModelManager.get().getUserModel().getFilterLevel();
        if (filterLevel != null && filterLevel.isEmpty())
            ModelManager.get().getUserModel().putFilterLevel("3.5");

        currentFilterLevel = ModelManager.get().getUserModel().getFilterLevel();
    }

    @Override
    public void onActivityResume() {
        String filterLevel = ModelManager.get().getUserModel().getFilterLevel();
        getContract().updateFilterLevel(filterLevel);
    }

    @Override
    public void onActivityPause() {

    }

    @Override
    public void onActivityDestroy() {

    }

    public void updateCurrentLocationDetail(Location location) {
        geoAPI = RemoteProxy.reflect(GoogleGeoAPI.class, this);

        this.mCurrentLocation = location;
        geoAPI.getMyLocation(location.getLatitude() + "," + location.getLongitude(), "zh-TW", true);
    }

    public void getCafesData(int city) {
        api = RemoteProxy.reflect(API.class, this);

        switch (city) {
            case 0:
                if (!currentCityShortName.contains(City.TAIPEI.getCityName()) && !currentCityShortName.contains("新北"))
                    getContract().move(City.TAIPEI.getLatLng(), CafeMapActivity.DefaultZoomLevel);
                api.requestTaipeiCafes();
                break;
            case 1:
                if (!currentCityShortName.contains(City.HSINCHU.getCityName()))
                    getContract().move(City.HSINCHU.getLatLng(), CafeMapActivity.DefaultZoomLevel);
                api.requestHsinchuCafes();
                break;
            case 2:
                if (!currentCityShortName.contains(City.TAICHUNG.getCityName()))
                    getContract().move(City.TAICHUNG.getLatLng(), CafeMapActivity.DefaultZoomLevel);
                api.requestTaichungCafes();
                break;
            case 3:
                if (!currentCityShortName.contains(City.KAOHSIUNG.getCityName()))
                    getContract().move(City.KAOHSIUNG.getLatLng(), CafeMapActivity.DefaultZoomLevel);
                api.requestKaohsiungCafes();
                break;
        }
    }

    @Callback("getMyLocation")
    public void onMyLocationDetailResponse(GoogleGeoDetail response) {
        if ("OK".equals(response.getStatus())) {
            currentCityShortName = "";
            for (GoogleGeoDetail.ResultsBean resultsBean : response.getResults()) {
                if (!currentCityShortName.isEmpty()) {
                    break;
                }
                if (resultsBean.getTypes() != null && resultsBean.getTypes().size() > 0 && resultsBean.getTypes().get(0).equals("street_address")) {
                    List<GoogleGeoDetail.ResultsBean.AddressComponentsBean> addressComponents = resultsBean.getAddress_components();
                    for (GoogleGeoDetail.ResultsBean.AddressComponentsBean addressComponent : addressComponents) {
                        if (addressComponent.getTypes() != null && addressComponent.getTypes().size() > 0 && addressComponent.getTypes().get(0).equals("administrative_area_level_1")) {
                            currentCityShortName = addressComponent.getShort_name();
                            Config.logi("目前城市:" + currentCityShortName);
                            break;
                        }
                    }
                }
            }
        } else {
            //failed
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
        filterCafe(mType, currentFilterLevel);
    }

    public void onCameraIdle() {
        filterCafe(mType, currentFilterLevel, false, true);
    }

    public void searchNearCafe(Location mCurrentLocation, boolean filterAll) {
        Config.loge("search " + mCurrentLocation);
        ArrayList<Cafe> cafeList = ModelManager.get().getUserModel().getCafeList();
        Cafe neaiestCafe = null;
        Integer neaiestDistance = -1;
        LatLng myLocation = MapUtils.parseToLatLng(mCurrentLocation);
        if (cafeList != null) {
            for (Cafe cafe : cafeList) {
                if (mType != FilterType.ALL) {
                    if (!filterAll && cafe.getScoreByFilterType(mType) < Double.valueOf(currentFilterLevel))
                        continue;
                }
                String[] distance = MapUtils.getDistance(myLocation, new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude())));
                Integer distanceM = Integer.valueOf(distance[0]);
                if (neaiestDistance == -1) {
                    neaiestCafe = cafe;
                    neaiestDistance = distanceM;
                } else {
                    neaiestDistance = Math.min(distanceM, neaiestDistance);
                    if (distanceM.intValue() == neaiestDistance.intValue()) {
                        neaiestCafe = cafe;
                    }
                }
            }
        }
        getContract().showNearMyCafe(neaiestCafe);
    }

    public void filterCafe(FilterType type, String filterLevel) {
        filterCafe(type, filterLevel, true, false);
    }

    public void filterCafe(FilterType type, String filterLevel, boolean clearMarker, boolean skipAlert) {
        this.mType = type;
        if (!filterLevel.isEmpty())
            this.currentFilterLevel = filterLevel;
        ArrayList<Cafe> cafeList = ModelManager.get().getUserModel().getCafeList();
        if (cafeList != null) {
            if (mType == FilterType.ALL) {
                getContract().updateCafeList(city, cafeList);
                return;
            }
            ArrayList<Cafe> afterFilterList = new ArrayList<>();

            for (Cafe cafe : cafeList) {
                double scoreByFilterType = cafe.getScoreByFilterType(type);
                if (scoreByFilterType >= Double.valueOf(filterLevel)) {
                    afterFilterList.add(cafe);
                }
            }
            if (clearMarker)
                getContract().clearMarker();
            getContract().updateCafeList(city, afterFilterList);
        } else {
            if (!skipAlert)
                getContract().onCafesDataIsEmpty();
        }
    }


}
