package tw.yalan.cafeoffice;

import com.grasea.grandroid.app.GrandroidApplication;

/**
 * Created by Alan Ding on 2016/12/19.
 */
public class CafeOfficeApplication extends GrandroidApplication {
    private static CafeOfficeApplication ourInstance = new CafeOfficeApplication();

    public static CafeOfficeApplication getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
    }
}
