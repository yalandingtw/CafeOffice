package tw.yalan.cafeoffice;

import android.util.Log;


public class Config {
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static String TAG = BuildConfig.TAG;

    public static final String EXTRA_DATA = "DATA";
    public static final String EXTRA_DATA2 = "DATA2";

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_KEY = "KEY";
    public static final String EXTRA_BOOLEAN = "BOOLEAN";
    public static final String EXTRA_ID_CHOOSE = "ID_CHOOSE";
    public static final String EXTRA_INDEX = "INDEX";
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_TYPE = "TYPE";
    public static String EXTRA_TIME = "TIME";
    //public static final int RESCODESUBMITTEAM = 60001;


    public static void logd(String message) {
        if (DEBUG) {
            Log.d(Config.TAG, message);
        }
    }

    public static void logd(Boolean message) {
        if (DEBUG) {
            Log.d(Config.TAG, message.toString());
        }
    }

    public static void loge(String message) {
        if (DEBUG) {
            Log.e(Config.TAG, message);
        }
    }

    public static void loge(Throwable th) {
        if (DEBUG) {
            Log.e(Config.TAG, null, th);
        }
    }

    public static void logi(String message) {
        if (DEBUG) {
            Log.i(Config.TAG, message);
        }
    }

    public static void logi(Boolean message) {
        if (DEBUG) {
            Log.i(Config.TAG, message.toString());
        }
    }

}

