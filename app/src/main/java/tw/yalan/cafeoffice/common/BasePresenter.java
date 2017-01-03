package tw.yalan.cafeoffice.common;

import android.location.Location;
import android.os.Bundle;

import com.grasea.grandroid.api.RemoteProxy;
import com.grasea.grandroid.mvp.GrandroidActivity;
import com.grasea.grandroid.mvp.GrandroidPresenter;

import tw.yalan.cafeoffice.api.API;
import tw.yalan.cafeoffice.cafemap.CafeMapPresenter;


/**
 * 主要父類別 Presenter
 * Created by Alan Ding on 2016/5/26.
 */
public abstract class BasePresenter<C extends GrandroidActivity> extends GrandroidPresenter<C> {
    API api;

    public abstract void onActivityCreate(Bundle extras);

    public abstract void onActivityResume();

    public abstract void onActivityPause();

    public abstract void onActivityDestroy();

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onRestoreInstanceState(Bundle outState) {

    }

    public API getApi(Object object) {
        if (api == null) {
            api = RemoteProxy.reflect(API.class, object);
        }
        return api;
    }


}
