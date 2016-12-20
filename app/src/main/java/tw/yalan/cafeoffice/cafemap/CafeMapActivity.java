package tw.yalan.cafeoffice.cafemap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import tw.yalan.cafeoffice.CafeDetailLayout;
import tw.yalan.cafeoffice.Config;
import tw.yalan.cafeoffice.R;
import tw.yalan.cafeoffice.adapter.MenuRecyclerAdapter;
import tw.yalan.cafeoffice.common.ColorfulDividerItemDecoration;
import tw.yalan.cafeoffice.common.LocatorActivity;
import tw.yalan.cafeoffice.common.NavigationAction;
import tw.yalan.cafeoffice.model.Cafe;
import tw.yalan.cafeoffice.model.MenuItem;
import tw.yalan.cafeoffice.utils.MapUtils;

@UsingPresenter(value = CafeMapPresenter.class, singleton = false)
public class CafeMapActivity extends LocatorActivity<CafeMapPresenter> implements OnMapReadyCallback {
    private static final int DefaultZoomLevel = 16;
    @BindView(R.id.recycler_menu)
    RecyclerView recyclerMenu;
    MenuRecyclerAdapter adapter;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.layout_cafe_detail)
    CafeDetailLayout layoutCafeDetail;
    @BindView(R.id.detail_layout)
    RelativeLayout detailLayout;
    @BindView(R.id.tv_near)
    TextView tvNear;

    private GoogleMap mMap;
    private VisibleRegion mCurrentVisibleRegion;
    private boolean isMoveToMyLocation = false;
    private Location mCurrentLocation = null;
    private int currentCity = -1;
    private ConcurrentHashMap<String, Marker> mMarkerMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Marker, Cafe> mCafeMap = new ConcurrentHashMap<>();
    private List<Cafe> cafeList;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_map);
        ButterKnife.bind(this);
        initMap();
        initSlideMenu();
        startFindMyLocation();
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
                        mMarkerMap.clear();
                        mCafeMap.clear();
                        mMap.clear();
                        getPresenter().getCafesData(mCurrentLocation, index);
                        break;
                    case 4:
                        String url = "https://github.com/ch8154";
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
        mMap.setOnMapClickListener(latLng -> animateSlideOutFooter());
        mMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            Cafe cafe = mCafeMap.get(marker);
            if (cafe != null) {
                layoutCafeDetail.setCafe(cafe);
                layoutCafeDetail.getBtnNavigation().setOnClickListener(view -> new NavigationAction(CafeMapActivity.this
                        , MapUtils.parseToLatLng(mCurrentLocation)
                        , new LatLng(Double.valueOf(cafe.getLatitude())
                        , Double.valueOf(cafe.getLongitude()))
                        , NavigationAction.DRIVING).execute());
                if (detailLayout.getVisibility() == View.INVISIBLE) {
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

    private void move(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mMap.getCameraPosition().zoom));
    }

    private void animateSlideInFooter(final Cafe cafe) {
        handler.post(() -> {
            mMap.setPadding(0, 0, 0, detailLayout.getHeight());
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                    , Animation.RELATIVE_TO_SELF, -detailLayout.getHeight(), Animation.RELATIVE_TO_SELF, 0);
            translateAnimation.setDuration(500);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    detailLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    move(new LatLng(Double.valueOf(cafe.getLatitude()), Double.valueOf(cafe.getLongitude())));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            detailLayout.startAnimation(translateAnimation);
        });
    }

    private void animateSlideOutFooter() {
        handler.post(() -> {
            mMap.setPadding(0, 0, 0, 0);
            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                    , Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -detailLayout.getHeight());
            translateAnimation.setDuration(500);
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    detailLayout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            detailLayout.startAnimation(translateAnimation);
        });

    }

    @Override
    public void updateCurrentLocation(Location location) {
        this.mCurrentLocation = location;
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
        if (mMap == null) {
            return;
        }
        this.cafeList = cafes;
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
            showLoadingDialog("查詢中...");
            Executors.newSingleThreadExecutor().submit(() -> getPresenter().searchNearCafe(mCurrentLocation));
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
        handler.post(() -> {
            if (cafe == null) {
                Config.loge("沒找到最近的咖啡廳");
                hideLoadingDialog();
                return;
            }
            if (mMarkerMap.contains(cafe.getId())) {
                Marker marker = mMarkerMap.get(cafe.getId());
                marker.showInfoWindow();
                if (detailLayout.getVisibility() == View.INVISIBLE) {
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
            handler.post(() -> {
                Marker marker = mMap.addMarker(options);
                mCafeMap.put(marker, cafe);
                mMarkerMap.put(cafe.getId(), marker);
            });
            showNearMyCafe(cafe);
        });
    }

//    public void showMarker(Marker marker) {
//
//    }
}