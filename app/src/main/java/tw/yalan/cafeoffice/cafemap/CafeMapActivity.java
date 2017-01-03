package tw.yalan.cafeoffice.cafemap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.grasea.grandroid.adapter.GrandroidRecyclerAdapter;
import com.grasea.grandroid.mvp.UsingPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tw.yalan.cafeoffice.common.actions.SearchAction;
import tw.yalan.cafeoffice.views.CafeDetailLayout;
import tw.yalan.cafeoffice.Config;
import tw.yalan.cafeoffice.R;
import tw.yalan.cafeoffice.adapter.MenuRecyclerAdapter;
import tw.yalan.cafeoffice.common.ColorfulDividerItemDecoration;
import tw.yalan.cafeoffice.common.FilterType;
import tw.yalan.cafeoffice.common.LocatorActivity;
import tw.yalan.cafeoffice.common.actions.NavigationAction;
import tw.yalan.cafeoffice.model.Cafe;
import tw.yalan.cafeoffice.model.MenuItem;
import tw.yalan.cafeoffice.setting.SettingActivity;
import tw.yalan.cafeoffice.utils.MapUtils;
import tw.yalan.cafeoffice.views.FilterLevelPicker;

@UsingPresenter(value = CafeMapPresenter.class, singleton = false)
public class CafeMapActivity extends LocatorActivity<CafeMapPresenter> implements OnMapReadyCallback {
    public static final int DefaultZoomLevel = 16;
    @BindView(R.id.recycler_menu)
    RecyclerView recyclerMenu;
    MenuRecyclerAdapter adapter;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.layout_cafe_detail)
    CafeDetailLayout cafeDetailLayout;
    @BindView(R.id.detail_layout)
    RelativeLayout detailContainer;
    @BindView(R.id.tv_near)
    TextView tvNear;
    @BindView(R.id.btn_wifi)
    FloatingActionButton btnWifi;
    @BindView(R.id.btn_cheap)
    FloatingActionButton btnCheap;
    @BindView(R.id.btn_seat)
    FloatingActionButton btnSeat;
    @BindView(R.id.btn_quiet)
    FloatingActionButton btnQuiet;
    @BindView(R.id.btn_music)
    FloatingActionButton btnSound;
    @BindView(R.id.btn_cafe)
    FloatingActionButton btnCafe;
    @BindView(R.id.btn_all)
    FloatingActionButton btnAll;
    @BindView(R.id.multiple_actions_down)
    FloatingActionsMenu btnMainFloating;

    private GoogleMap mMap;
    private VisibleRegion mCurrentVisibleRegion;
    private boolean isMoveToMyLocation = false;
    private Location mCurrentLocation = null;
    private int currentCity = -1;
    private ConcurrentHashMap<String, Marker> mMarkerMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Marker, Cafe> mCafeMap = new ConcurrentHashMap<>();
    private Handler handler = new Handler();
    private String filterLevel = "3.5";
    private FilterLevelPicker filterLevelPicker;

    public void clearMarker() {
        Config.loge("Clear Marker");
        if (mMap != null) {
            handler.post(() -> mMap.clear());
        }
//        for (Marker marker : mMarkerMap.values()) {
//            marker.remove();
//        }
        mMarkerMap.clear();
        mCafeMap.clear();
    }

    public void onCafesDataIsEmpty() {
        alert(getString(R.string.alert_data_empty));
    }

    @OnClick({R.id.btn_all, R.id.btn_cheap, R.id.btn_seat, R.id.btn_quiet, R.id.btn_music, R.id.btn_cafe, R.id.btn_wifi, R.id.multiple_actions_down})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_all) {
            btnMainFloating.collapse();
            getPresenter().filterCafe(FilterType.ALL, "");
        } else if (view.getId() != R.id.multiple_actions_down) {
            if (filterLevelPicker == null) {
                filterLevelPicker = new FilterLevelPicker(this, getString(R.string.picker_title_filter_low));
                filterLevelPicker.setmOnDismissListener(new FilterLevelPicker.OnDismissListener() {
                    @Override
                    public void onSelected(String newSelectedString) {
                        switch (view.getId()) {
                            case R.id.btn_cheap:
                                btnMainFloating.collapse();
                                getPresenter().filterCafe(FilterType.CHEAP, newSelectedString);
                                break;
                            case R.id.btn_seat:
                                btnMainFloating.collapse();
                                getPresenter().filterCafe(FilterType.SEAT, newSelectedString);

                                break;
                            case R.id.btn_quiet:
                                btnMainFloating.collapse();
                                getPresenter().filterCafe(FilterType.QUIET, newSelectedString);

                                break;
                            case R.id.btn_music:
                                btnMainFloating.collapse();
                                getPresenter().filterCafe(FilterType.MUSIC, newSelectedString);

                                break;
                            case R.id.btn_cafe:
                                btnMainFloating.collapse();
                                getPresenter().filterCafe(FilterType.CAFE, newSelectedString);
                                break;
                            case R.id.btn_wifi:
                                btnMainFloating.collapse();
                                getPresenter().filterCafe(FilterType.WIFI, newSelectedString);
                                break;
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
            String[] split = filterLevel.split("[.]");
            filterLevelPicker.setValue(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            filterLevelPicker.show();
        }

    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_cafe_map);
        ButterKnife.bind(this);
        initMap();
        initSlideMenu();
        startFindMyLocation();
        hideAllFilterButton();
        btnMainFloating.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                showAllFilterButton();
            }

            @Override
            public void onMenuCollapsed() {
                hideAllFilterButton();
            }
        });
    }

    private void showAllFilterButton() {
        if (btnAll != null) {
            btnAll.setVisibility(View.VISIBLE);
        }
        if (btnCafe != null) {
            btnCafe.setVisibility(View.VISIBLE);
        }
        if (btnSound != null) {
            btnSound.setVisibility(View.VISIBLE);
        }
        if (btnWifi != null) {
            btnWifi.setVisibility(View.VISIBLE);
        }
        if (btnSeat != null) {
            btnSeat.setVisibility(View.VISIBLE);
        }
        if (btnCheap != null) {
            btnCheap.setVisibility(View.VISIBLE);
        }
        if (btnQuiet != null) {
            btnQuiet.setVisibility(View.VISIBLE);
        }
    }

    private void hideAllFilterButton() {
        if (btnAll != null) {
            btnAll.setVisibility(View.GONE);
        }
        if (btnCafe != null) {
            btnCafe.setVisibility(View.GONE);
        }
        if (btnSound != null) {
            btnSound.setVisibility(View.GONE);
        }
        if (btnWifi != null) {
            btnWifi.setVisibility(View.GONE);
        }
        if (btnSeat != null) {
            btnSeat.setVisibility(View.GONE);
        }
        if (btnCheap != null) {
            btnCheap.setVisibility(View.GONE);
        }
        if (btnQuiet != null) {
            btnQuiet.setVisibility(View.GONE);
        }
    }

    private void initSlideMenu() {
        setSupportActionBar(getToolbar());

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, getToolbar(), R.string.open_str, R.string.close_str);
        drawerToggle.syncState();
        drawer.addDrawerListener(drawerToggle);
        adapter = new MenuRecyclerAdapter(this, new ArrayList<>(), new MenuRecyclerAdapter.SimpleItemConfig()) {
            @Override
            public void onItemClick(ViewHolder holder, int index, ItemObject item) {
                drawer.closeDrawers();
                switch (index) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        if (currentCity == index) {
                            return;
                        }
                        currentCity = index;
                        clearMarker();
                        getPresenter().getCafesData(index);
                        break;
                    case 4://設定
                        changeToActivity(SettingActivity.class, null);
                        break;
                    case 5://GitHub
                        String url = "https://github.com/ch8154/CafeOffice";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;
                }
            }
        };
        adapter.setChooseMode(GrandroidRecyclerAdapter.ChooseMode.SINGLE_RADIO);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenu.setAdapter(adapter);
        recyclerMenu.addItemDecoration(new ColorfulDividerItemDecoration(1, ContextCompat.getColor(this, R.color.md_grey_400)));
        adapter.getList().add(new MenuRecyclerAdapter.ItemObject(new MenuItem(R.drawable.ic_search, "找台北")));
        adapter.getList().add(new MenuRecyclerAdapter.ItemObject(new MenuItem(R.drawable.ic_search, "找新竹")));
        adapter.getList().add(new MenuRecyclerAdapter.ItemObject(new MenuItem(R.drawable.ic_search, "找台中")));
        adapter.getList().add(new MenuRecyclerAdapter.ItemObject(new MenuItem(R.drawable.ic_search, "找高雄")));
        adapter.getList().add(new MenuRecyclerAdapter.ItemObject(new MenuItem(R.drawable.ic_settings_black_24dp, "設定")));
        adapter.getList().add(new MenuRecyclerAdapter.ItemObject(new MenuItem(R.drawable.ic_github, "GitHub")));

    }

    public void initMap() {
        getMapFragment().getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnCameraIdleListener(() -> {
//            Config.loge("OnCameraIdle");
            mCurrentVisibleRegion = mMap.getProjection().getVisibleRegion();
            getPresenter().onCameraIdle();
        });
        mMap.setOnMapClickListener(latLng -> {
            if (detailContainer.getVisibility() == View.VISIBLE)
                animateSlideOutFooter();
            if (btnMainFloating.isExpanded()) {
                btnMainFloating.collapse();
            }
        });
        mMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            Cafe cafe = mCafeMap.get(marker);
            if (cafe != null) {
                cafeDetailLayout.setCafe(cafe);
                cafeDetailLayout.getBtnNavigation().setOnClickListener(view -> new NavigationAction(CafeMapActivity.this
                        , MapUtils.parseToLatLng(mCurrentLocation)
                        , new LatLng(Double.valueOf(cafe.getLatitude())
                        , Double.valueOf(cafe.getLongitude()))
                        , NavigationAction.DRIVING).execute());

                cafeDetailLayout.getBtnSearchGoogle().setOnClickListener(view -> new SearchAction(this, cafe.getName()).execute());
                if (detailContainer.getVisibility() == View.INVISIBLE) {
                    animateSlideInFooter(cafe);
                } else {
                    move(new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude())));
                }
            }
            return true;
        });
        mCurrentVisibleRegion = mMap.getProjection().getVisibleRegion();
        if (!isMoveToMyLocation && mCurrentLocation != null) {
            moveToMyLocation();
        }
    }

    public void move(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mMap.getCameraPosition().zoom));
    }

    public void move(LatLng latLng, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void animateSlideInFooter(final Cafe cafe) {
        handler.post(() -> {
            mMap.setPadding(0, 0, 0, detailContainer.getHeight());
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                    , Animation.RELATIVE_TO_SELF, -detailContainer.getHeight(), Animation.RELATIVE_TO_SELF, 0);
            translateAnimation.setDuration(500);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    detailContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    move(new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude())));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            detailContainer.startAnimation(translateAnimation);
        });
    }

    private void animateSlideOutFooter() {
        handler.post(() -> {
            mMap.setPadding(0, 0, 0, 0);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                    , Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -detailContainer.getHeight());
            translateAnimation.setDuration(500);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    detailContainer.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            detailContainer.startAnimation(translateAnimation);
        });

    }

    @Override
    public void updateCurrentLocation(Location location) {
        this.mCurrentLocation = location;
        getPresenter().updateCurrentLocationDetail(location);
        if (mMap != null) {
            isMoveToMyLocation = true;
            Config.loge("update My Location:" + location.getLatitude() + "/" + location.getLongitude());
            moveToMyLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }

    }

    public void moveToMyLocation() {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(
                MapUtils.parseToLatLng(mCurrentLocation), DefaultZoomLevel)));
    }

    private SupportMapFragment getMapFragment() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        return fragment;
    }

    public void updateCafeList(String city, List<Cafe> cafes) {
        if (cafes != null)
            Config.loge("總共可顯示筆數:" + cafes.size());
        if (mMap == null) {
            return;
        }
        Executors.newSingleThreadExecutor().submit(() -> {
            for (Cafe cafe : cafes) {
                try {
                    fillDistance(cafe);
                    LatLng latLng = new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude()));
                    boolean contains = mCurrentVisibleRegion.latLngBounds.contains(latLng);
                    if (contains) {
                        if (mMarkerMap.contains(cafe.getId())) {
                            continue;
                        }
//                        Config.loge("add marker:" + cafe.getName());
                        MarkerOptions options = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place))
                                .position(latLng)
                                .title(cafe.getName());
                        handler.post(() -> {
                            Marker marker = mMap.addMarker(options);
                            mCafeMap.put(marker, cafe);
                            mMarkerMap.put(cafe.getId(), marker);
                        });


                    } else {
                        Marker marker = mMarkerMap.get(cafe.getId());
                        if (marker != null) {
//                            Config.loge("remove marker:" + cafe.getName());
                            mCafeMap.remove(marker);
                            handler.post(() -> marker.remove());
                        }
                        mMarkerMap.remove(cafe.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @OnClick(R.id.tv_near)
    public void onClick() {
        if (mCurrentLocation != null) {
            showLoadingDialog(getString(R.string.searching), true);
            getPresenter().searchNearCafe(mCurrentLocation, false);
        }
    }

    public void fillDistance(Cafe cafe) {
        String[] distance = MapUtils.getDistance(MapUtils.parseToLatLng(mCurrentLocation)
                , new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude())));
        if (Integer.valueOf(distance[0]) >= 1000) {
            cafe.setDistance(distance[1] + " KM");
        } else {
            cafe.setDistance(distance[0] + " M");
        }
    }

    public void showNearMyCafe(Cafe cafe) {
        if (cafe == null) {
            alert(getString(R.string.alert_donot_find));
            hideLoadingDialog();
            return;
        }
        if (mMarkerMap.containsKey(cafe.getId())) {
            Marker marker = mMarkerMap.get(cafe.getId());
            marker.showInfoWindow();
            cafeDetailLayout.setCafe(cafe);
            cafeDetailLayout.getBtnNavigation().setOnClickListener(view -> new NavigationAction(CafeMapActivity.this
                    , MapUtils.parseToLatLng(mCurrentLocation)
                    , new LatLng(Double.valueOf(cafe.getLatitude())
                    , Double.valueOf(cafe.getLongitude()))
                    , NavigationAction.DRIVING).execute());

            cafeDetailLayout.getBtnSearchGoogle().setOnClickListener(view -> new SearchAction(this, cafe.getName()).execute());
            if (detailContainer.getVisibility() == View.INVISIBLE) {
                animateSlideInFooter(cafe);
            } else {
                move(new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude())));
            }
            hideLoadingDialog();
            return;
        }

        fillDistance(cafe);
        LatLng latLng = new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude()));
//                        Config.loge("add marker:" + cafe.getName());
        MarkerOptions options = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place))
                .position(latLng)
                .title(cafe.getName());
        Marker marker = mMap.addMarker(options);
        mCafeMap.put(marker, cafe);
        mMarkerMap.put(cafe.getId(), marker);
        showNearMyCafe(cafe);
    }

    public void updateFilterLevel(String filterLevel) {
        this.filterLevel = filterLevel;
    }

//    public void showMarker(Marker marker) {
//
//    }
}
