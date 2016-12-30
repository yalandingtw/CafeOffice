package tw.yalan.cafeoffice.setting;

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

import android.os.Bundle;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import tw.yalan.cafeoffice.CafeOfficeApplication;
import tw.yalan.cafeoffice.R;
import tw.yalan.cafeoffice.common.BasePresenter;
import tw.yalan.cafeoffice.data.ModelManager;
import tw.yalan.cafeoffice.model.SettingItem;

/**
 * Created by Alan Ding on 2016/12/29.
 */
public class SettingPresenter extends BasePresenter<SettingActivity> {
    ArrayList<SettingItem> settingItems = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("0.0");

    @Override
    public void onActivityCreate(Bundle extras) {
        String filterLevel = ModelManager.get().getUserModel().getFilterLevel();
        if (filterLevel.isEmpty()) {
            filterLevel = "3.5";
            ModelManager.get().getUserModel().putFilterLevel(filterLevel);
        }

        settingItems.add(new SettingItem(CafeOfficeApplication.getInstance().getString(R.string.setting_item_filter), df.format(Double.valueOf(filterLevel))));
        getContract().initViews();
        getContract().updateSettingList(settingItems);
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

    public void onUpdateNewFilterLevel(Double filterLevel) {
        ModelManager.get().getUserModel().putFilterLevel(df.format(filterLevel));
        settingItems.get(0).setValue(String.valueOf(filterLevel));
        getContract().updateSettingList(settingItems);
    }
}
