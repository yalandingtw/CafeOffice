package tw.yalan.cafeoffice.views;

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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tw.yalan.cafeoffice.R;
import tw.yalan.cafeoffice.common.NavigationAction;
import tw.yalan.cafeoffice.model.Cafe;

/**
 * Created by Alan Ding on 2016/12/20.
 */
public class CafeDetailLayout extends RelativeLayout {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_rating_wifi)
    TextView tvRatingWifi;
    @BindView(R.id.tv_rating_seat)
    TextView tvRatingSeat;
    @BindView(R.id.tv_rating_quiet)
    TextView tvRatingQuiet;
    @BindView(R.id.tv_rating_tasty)
    TextView tvRatingTasty;
    @BindView(R.id.tv_rating_cheap)
    TextView tvRatingCheap;
    @BindView(R.id.tv_rating_music)
    TextView tvRatingMusic;

    Cafe cafe;
    @BindView(R.id.btn_navigation)
    Button btnNavigation;

    public CafeDetailLayout(Context context) {
        super(context);
        init();
    }

    public CafeDetailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CafeDetailLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CafeDetailLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_cafe_detail_footer, this, true);
        ButterKnife.bind(this, view);
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
        tvName.setText(cafe.getName());
        tvAddress.setText(cafe.getAddress());
        tvDistance.setText(cafe.getDistance());
        tvRatingWifi.setText(String.valueOf(cafe.getWifi()));
        tvRatingCheap.setText(String.valueOf(cafe.getCheap()));
        tvRatingMusic.setText(String.valueOf(cafe.getMusic()));
        tvRatingQuiet.setText(String.valueOf(cafe.getQuiet()));
        tvRatingSeat.setText(String.valueOf(cafe.getSeat()));
        tvRatingTasty.setText(String.valueOf(cafe.getTasty()));
    }

    public Button getBtnNavigation() {
        return btnNavigation;
    }

}
