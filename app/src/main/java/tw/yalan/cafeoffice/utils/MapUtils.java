package tw.yalan.cafeoffice.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import tw.yalan.cafeoffice.Config;

/**
 * Created by Alan Ding on 2016/3/2.
 */
public class MapUtils {
    /**
     * 產生GoogleApiClient
     *
     * @param context
     * @param connectionCallbacks
     * @param connectionFailedListener
     * @return
     */
    public static synchronized GoogleApiClient buildGoogleApiClient(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks
            , GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
        return new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionFailedListener)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * GPS是否啟用
     *
     * @param context
     * @return
     */
    public static Boolean checkGPSEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 計算兩點距離
     *
     * @param latLng1
     * @param latLng2
     * @return result[0]:公尺/result[1]:公里
     */
    public static String[] getDistance(LatLng latLng1, LatLng latLng2) {
        float[] results = new float[1];
        Location.distanceBetween(latLng1.latitude, latLng1.longitude, latLng2.latitude, latLng2.longitude, results);
        String[] stirngResult = new String[2];
        stirngResult[0] = String.valueOf((int) (results[0] * 1.609344));
        BigDecimal m = new BigDecimal((results[0] * 1.609344));
        stirngResult[1] = String.valueOf(m.divide(new BigDecimal(1000f), 2, BigDecimal.ROUND_HALF_UP));
        return stirngResult;
    }

    /**
     * 經緯度轉換地址
     *
     * @param context
     * @param lat
     * @param lon
     * @return
     */
    public static Address convertToAddress(Context context, Double lat, Double lon) {
        Geocoder geocoder = new Geocoder(context, Locale.TRADITIONAL_CHINESE);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            Log.e(Config.TAG, null, e);
        }
        if (addresses != null && !addresses.isEmpty()) {
            return addresses.get(0);
        }
        return null;
    }

    public static LatLng parseToLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
