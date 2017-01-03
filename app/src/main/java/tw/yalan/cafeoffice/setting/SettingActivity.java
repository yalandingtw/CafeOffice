package tw.yalan.cafeoffice.setting;

import android.databinding.ObservableArrayList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.github.nitrico.lastadapter.ItemType;
import com.github.nitrico.lastadapter.LastAdapter;
import com.grasea.grandroid.mvp.UsingPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import tw.yalan.cafeoffice.BR;
import tw.yalan.cafeoffice.R;
import tw.yalan.cafeoffice.common.BaseActivity;
import tw.yalan.cafeoffice.common.ColorfulDividerItemDecoration;
import tw.yalan.cafeoffice.databinding.RowSettingBinding;
import tw.yalan.cafeoffice.model.SettingItem;
import tw.yalan.cafeoffice.utils.PickerFactory;
import tw.yalan.cafeoffice.views.FilterLevelPicker;

@UsingPresenter(value = SettingPresenter.class, singleton = false)
public class SettingActivity extends BaseActivity<SettingPresenter> {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    ObservableArrayList<SettingItem> items = new ObservableArrayList<>();
    FilterLevelPicker filterLevelPicker;
    private final ItemType<RowSettingBinding> TYPE_SETTING = new ItemType<RowSettingBinding>(R.layout.row_setting) {
        @Override
        public void onBind(@NotNull RowSettingBinding binding, @NotNull View view, int position) {
            view.setOnClickListener(itemView -> {
                switch (position) {
                    case 0://Filter
                        String value = binding.getItem().getValue();
                        String[] defaultValues = value.split("[.]");
                        filterLevelPicker.setValue(Integer.valueOf(defaultValues[0]), Integer.valueOf(defaultValues[1]));
                        filterLevelPicker.show();
                        break;
                }
            });
        }

        @Override
        public void onRecycle(@NotNull RowSettingBinding binding, @NotNull View view, int position) {
        }
    };

    @Override
    public void initViews() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
        filterLevelPicker = new FilterLevelPicker(this);
        filterLevelPicker.setmOnDismissListener(new FilterLevelPicker.OnDismissListener() {
            @Override
            public void onSelected(String newSelectedString) {
                getPresenter().onUpdateNewFilterLevel(Double.valueOf(newSelectedString));
            }

            @Override
            public void onCancel() {

            }
        });
    }


    private void initRecyclerView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new ColorfulDividerItemDecoration(1, ContextCompat.getColor(this, R.color.md_grey_400)));
        LastAdapter.with(items, BR.item).map(SettingItem.class, TYPE_SETTING).into(recycler);
    }

    public void updateSettingList(List data) {
        items.clear();
        items.addAll(data);
    }

    private void initToolbar() {
        setSupportActionBar(getToolbar());
        if (getSupportActionBar() != null && getToolbar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getToolbar().setNavigationOnClickListener(view -> finish());
            getToolbar().setTitle(R.string.title_setting);
        }
    }

}
