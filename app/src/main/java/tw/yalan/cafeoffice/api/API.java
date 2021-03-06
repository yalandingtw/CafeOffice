package tw.yalan.cafeoffice.api;

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

import com.grasea.grandroid.api.Backend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tw.yalan.cafeoffice.BuildConfig;
import tw.yalan.cafeoffice.model.Cafe;

/**
 * Created by Alan Ding on 2016/9/10.
 */
@Backend(value = BuildConfig.API_URL + "v" + BuildConfig.API_VER + "/cafes/", debug = true)
public interface API {
    /**
     * 台北
     *
     * @return
     */
    @GET("taipei")
    Call<List<Cafe>> requestTaipeiCafes();

    /**
     * 新竹
     *
     * @return
     */
    @GET("hsinchu")
    Call<List<Cafe>> requestHsinchuCafes();

    /**
     * 台中
     *
     * @return
     */
    @GET("taichung")
    Call<List<Cafe>> requestTaichungCafes();

    /**
     * 高雄
     *
     * @return
     */
    @GET("kaohsiung")
    Call<List<Cafe>> requestKaohsiungCafes();
}
