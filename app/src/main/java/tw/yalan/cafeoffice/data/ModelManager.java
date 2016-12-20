package tw.yalan.cafeoffice.data;

import com.grasea.grandroid.mvp.model.ModelProxy;

/**
 * Created by Alan Ding on 2016/10/12.
 */
public class ModelManager {
    private static ModelManager ourInstance = new ModelManager();

    public static ModelManager get() {
        return ourInstance;
    }

    UserModel userModel;

    private ModelManager() {
    }


    public UserModel getUserModel() {
        if (userModel == null) {
            userModel = ModelProxy.reflect(UserModel.class);
        }
        return userModel;
    }

}
