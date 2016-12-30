package tw.yalan.cafeoffice.data;

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

import com.grasea.grandroid.mvp.model.DefaultValue;
import com.grasea.grandroid.mvp.model.Get;
import com.grasea.grandroid.mvp.model.Put;
import com.grasea.grandroid.mvp.model.Storage;

import java.util.ArrayList;

import tw.yalan.cafeoffice.model.Cafe;

/**
 * Created by Alan Ding on 2016/9/28.
 */
public interface UserModel {


    @Put(value = "UserChooseCity", storage = Storage.Memory)
    public boolean putUserChooseCity(int index);

    @Get(value = "UserChooseCity", storage = Storage.Memory, defaultValue = DefaultValue.ZERO)
    public int getUserChooseCity();


    @Put(value = "CafeList", storage = Storage.Memory)
    public boolean putCafeList(ArrayList<Cafe> cafeArrayList);

    @Get(value = "CafeList", storage = Storage.Memory, defaultValue = DefaultValue.NULL)
    public ArrayList<Cafe> getCafeList();

    @Put(value = "FilterLevel")
    public boolean putFilterLevel(String level);

    @Get(value = "FilterLevel", defaultValue = DefaultValue.EMPTY_STRING)
    public String getFilterLevel();

}
