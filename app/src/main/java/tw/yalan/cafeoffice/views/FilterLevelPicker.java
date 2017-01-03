package tw.yalan.cafeoffice.views;

/**
 * Copyright (C) 2016 Alan Ding
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use context file except in compliance with the License.
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

import android.content.Context;
import android.content.DialogInterface;
import android.icu.math.BigDecimal;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import tw.yalan.cafeoffice.utils.PickerFactory;

/**
 * Created by Alan Ding on 2016/12/30.
 */
public class FilterLevelPicker {
    public interface OnDismissListener {
        void onSelected(String newSelectedString);

        void onCancel();
    }

    private OnDismissListener mOnDismissListener;
    private AlertDialog filterDialog;
    private MaterialNumberPicker filterLeftPicker, filterRightPicker;
    private Context context;
    private String currentLevel;
    private String title = "設定";

    public FilterLevelPicker(Context context) {
        this.context = context;
        buildPickers();
    }

    public FilterLevelPicker(Context context, String title) {
        this.context = context;
        this.title = title;
        buildPickers();
    }

    public OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setmOnDismissListener(OnDismissListener mOnDismissListener) {
        this.mOnDismissListener = mOnDismissListener;
    }

    public void setValue(int left, int right) {
        filterLeftPicker.setValue(left);
        filterRightPicker.setValue(right);
        currentLevel = left + "." + right;
    }

    public void show() {
        if (filterDialog.isShowing()) {
            return;
        }
        filterDialog.show();
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    private void buildPickers() {
        LinearLayout pickerLayout = new LinearLayout(context);
        TextView tvDot = new TextView(context);
        tvDot.setText(".");
        tvDot.setGravity(Gravity.BOTTOM);
        filterLeftPicker = PickerFactory.createNumberPicker(context, 0, 5, 3, null);
        filterLeftPicker.setOnValueChangedListener((numberPicker, oldVal, newVal) -> {
            if (newVal == 5) {
                filterRightPicker.setValue(0);
                filterRightPicker.setMaxValue(0);
            } else {
                if (oldVal == 5)
                    filterRightPicker.setMaxValue(9);
            }
        });
        filterRightPicker = PickerFactory.createNumberPicker(context, 0, 9, 5, null);
        pickerLayout.addView(filterLeftPicker);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pickerLayout.addView(tvDot, layoutParams);
        pickerLayout.addView(filterRightPicker);
        pickerLayout.setGravity(Gravity.CENTER);
        pickerLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        AlertDialog.Builder builder = PickerFactory.createAlertDialog(context, title)
                .setNegativeButton("確定", (dialogInterface, i) -> {
                    String newLevel = String.valueOf(filterLeftPicker.getValue()) + "." + String.valueOf(filterRightPicker.getValue());
                    currentLevel = newLevel;
                    if (mOnDismissListener != null)
                        mOnDismissListener.onSelected(newLevel);
                }).setPositiveButton("取消", (dialogInterface, i) -> {
                    if (mOnDismissListener != null)
                        mOnDismissListener.onCancel();
                });
        builder.setView(pickerLayout);
        filterDialog = builder.create();
    }


}
