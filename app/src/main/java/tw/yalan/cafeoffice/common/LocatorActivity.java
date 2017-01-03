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


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.List;

import tw.yalan.cafeoffice.Config;
import tw.yalan.cafeoffice.data.ModelManager;
import tw.yalan.cafeoffice.model.Cafe;
import tw.yalan.cafeoffice.utils.GeoLocator;

/**
 * Created by Alan Ding on 2016/12/19.
 */
public abstract class LocatorActivity<P extends BasePresenter> extends BaseActivity<P> {
    private static final int REQUEST_PERMISSION = 999;
    private GeoLocator mLocator;
    private boolean mIsMoveMyLocation = false;
    private boolean mIsCalledStartLocate = false;
    private boolean mGotPremission = false;

    /**
     * 開始定位
     */
    public void startFindMyLocation() {
        mIsCalledStartLocate = true;
        if (!checkPermission()) {
            return;
        }

        GeoLocator locator = getLocator();
        GeoLocator.LocationResult locationResult = new GeoLocator.LocationResult() {
            @Override
            public boolean gotLocation(Location location) {
                if (!mIsMoveMyLocation) {
                    mIsMoveMyLocation = true;
                    updateCurrentLocation(location);
                } else {
                    this.setMaxCount(1);
                }
                return true;
            }
        }.follow();
        locator.addLocatingJob(locationResult);
        locator.start();
        Config.loge("start Locate.");
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION);
            mGotPremission = false;
            Config.loge("request permissions");
            return false;
        }
        mGotPremission = true;
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    Config.loge("request permissions failed");
                    return;
                }
            }
            if (mIsCalledStartLocate) {
                Config.loge("request permissions success restart locate.");
                startFindMyLocation();
            }
        }
    }

    public abstract void updateCurrentLocation(Location location);

    public GeoLocator getLocator() {
        if (mLocator == null) {
            mLocator = new GeoLocator(this);
        }
        return mLocator;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getLocator() != null && mIsCalledStartLocate && mGotPremission) {
            getLocator().start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getLocator().isConnected()) {
            getLocator().stop();
        }
    }


}
