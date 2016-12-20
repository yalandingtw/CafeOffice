/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.yalan.cafeoffice.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Rovers
 */
public class GeoLocator implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {
    public static final long INTERVAL = 10000;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected boolean connected;
    protected List<LocationResult> callbacks;//採用Observer Pattern
    protected long interval = 0;//接收位置的間隔時間
    /**
     * 通过 LocationRequest.setFastestInterval() 设置。这一方法设置的是你的应用能处理更新的最快间隔时间
     * ，以毫秒为单位。你需要设置这个频率是因为其它应用也会影响位置更新非频率。
     * 定位服务会以所有应用通过 LocationRequest.setInterval() 设置的最快的间隔时间来发送更新。
     * 如果这一频率比你的应用能够处理的频率要快，那么你可能会遇到 UI 闪烁或数据溢出等问题。
     * 为了避免这一情况发生，应该调用 LocationRequest.setFastestInterval() 这一方法设置更新频率的最高限额。
     */
    protected long fastestInterval = 1;//App最快能處理位置更新的間格時間

    protected Context context;
//    protected boolean locateAfterConnected;

    public GeoLocator(Context context) {
        this.context = context;
        interval = INTERVAL;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = new LocationRequest();
        callbacks = new CopyOnWriteArrayList<LocationResult>();
    }

    public void setInterval(long interval) {
        this.interval = interval;
        mLocationRequest.setInterval(interval);
    }

    public long getInterval() {
        return interval;
    }

    public long getFastestInterval() {
        return fastestInterval;
    }

    public void setFastestInterval(long fastestInterval) {
        this.fastestInterval = fastestInterval;
        mLocationRequest.setFastestInterval(fastestInterval);
    }


    public Location getLastLocation() {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (isConnected() && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } else {
            return null;
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void start() {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            mGoogleApiClient.connect();
        } else {
            for (int i = 0; i < callbacks.size(); i++) {
                LocationResult callback = callbacks.get(i);
                callback.onNoDeviceSupport();
            }
        }

    }
//
//    public void startAndLocateOnce(boolean locateAfterConnected) {
//        this.locateAfterConnected = locateAfterConnected;
//        if (lm != null) {
//            if (!lm.isConnected() && !lm.isConnecting()) {
//                lm.connect();
//            }
//        }
//    }
//
//    public void startAndLocateContinuous(long interval) {
//        this.interval = interval;
//        this.locateAfterConnected = true;
//        if (lm != null) {
//            if (!lm.isConnected() && !lm.isConnecting()) {
//                lm.connect();
//            }
//        }
//    }

    public List<LocationResult> getLocationCallbacks() {
        return callbacks;
    }

    public GeoLocator addLocationCallback(LocationResult result) {
        callbacks.add(result);
        return this;
    }

    public void stop() {
        connected = false;
        interval = 0;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * 用在onCreated之內，地圖初始化完之後，執行一次性的定位工作 之後還是要呼叫start()
     *
     * @param result
     */
    public void addLocatingJob(LocationResult result) {
        addLocationCallback(result);
    }

    /**
     * 用在onCreated之內，地圖初始化完之後，開啟定時追蹤 之後還是要呼叫start()
     *
     * @param result
     * @param interval
     */
    public void addLocatingJob(LocationResult result, long interval) {
        this.interval = interval;
        result.reset();
        if (result.getMaxCount() <= 1) {
            result.setMaxCount(Integer.MAX_VALUE);
        }
        addLocationCallback(result);
    }

//    public void locateInterval(LocationResult result, long interval) {
//        if (waitingConnection() && !callbacks.contains(result)) {
//            this.interval = interval;
//            result.setMaxCount(Integer.MAX_VALUE);
//            addLocationCallback(result);
//            lm.requestLocationUpdates(LocationRequest.create().setInterval(interval), listener);
//        }
//    }
//
//    /**
//     * synchronize method, waiting for connected
//     *
//     * @param result
//     */
//    public void locateOnce(LocationResult result) {
//        if (waitingConnection()) {
//            if (interval > 0) {
//                result.gotLocation(lm.getLastLocation());
//            } else {
//                result.setMaxCount(1);
//                addLocationCallback(result);
//                lm.requestLocationUpdates(LocationRequest.create().setNumUpdates(1), listener);
//                //執行完後自然會從callbacks裡刪除
//            }
//        } else {
//            Log.e("grandroid", "cannot connect lm");
//        }
//    }

    /**
     * after calling start(), you can call this method to request locating job
     * manually.
     *
     * @return if request successfully
     */
    @SuppressWarnings("MissingPermission")
    public void onConnected(Bundle bundle) {
//        if (locateAfterConnected) {
//            int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
//            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
        connected = true;
        try {
            mLocationRequest.setInterval(getInterval()).setFastestInterval(getFastestInterval());
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status != null) {
                        Log.i("grandroid", status.getStatusCode() + "/" + status.getStatus());
                    }
                    if (callbacks.isEmpty()) {
                        Log.e("grandroid", "callbacks is Empty. Stop to request location update.");
                        stop();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("grandroid", "取得位置或地址失敗", e);
        }
//                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//            }
/*         \   if (interval == 0) {
                locateCustom(LocationRequest.create().setInterval(0), listener);
            } else {
                locateCustom(LocationRequest.create().setInterval(interval), listener);
            }*/
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onDisconnected() {
        connected = false;
    }

    public void onConnectionFailed(ConnectionResult cr) {
        Log.e("grandroid", "error while connect to google mapView serivce: " + cr.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("grandroid", "On Location Changed.");
        for (int i = callbacks.size() - 1; i >= 0; i--) {
            LocationResult result = callbacks.get(i);
            result.used();
            boolean keep = result.gotLocation(location);
            if (!keep || result.isExceedCount()) { //有任何一個callback回傳false
                //移除該callback
                callbacks.remove(i);
            }
        }
        if (callbacks.isEmpty()) {
            interval = 0;
            //stop request locating
            if (isConnected()) {
                stop();
            }
        }
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
    }

    public static abstract class LocationResult {
        protected int maxCount = 1;
        protected int executeCount = 0;

        public void reset() {
            maxCount = 1;
            executeCount = 0;
        }

        public boolean isExceedCount() {
            return executeCount >= maxCount;
        }

        public int getExecuteCount() {
            return executeCount;
        }

        public int getMaxCount() {
            return maxCount;
        }

        public void setMaxCount(int maxCount) {
            this.maxCount = maxCount;
        }

        public LocationResult once() {
            this.maxCount = 1;
            return this;
        }

        /**
         * 如要定時追蹤，需先呼叫此方法
         *
         * @return
         */
        public LocationResult follow() {
            this.maxCount = Integer.MAX_VALUE;
            return this;
        }

        public void used() {
            executeCount++;
        }

        public abstract boolean gotLocation(Location location);

        public void onNoDeviceSupport() {
        }

    }
}
