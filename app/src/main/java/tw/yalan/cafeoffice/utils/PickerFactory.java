package tw.yalan.cafeoffice.utils;

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

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by Alan Ding on 2016/8/25.
 */
public class PickerFactory {
    public static MaterialNumberPicker createNumberPicker(Context context, int min, int max, int defaultValue, NumberPicker.Formatter formatter) {
        MaterialNumberPicker.Builder builder = new MaterialNumberPicker.Builder(context)
                .minValue(min)
                .maxValue(max)
                .defaultValue(defaultValue)
                .backgroundColor(Color.TRANSPARENT)
                .separatorColor(Color.BLACK)
                .textColor(Color.BLACK)
                .textSize(20)
                .enableFocusability(false)
                .wrapSelectorWheel(true);
        if (formatter != null) {
            builder.formatter(formatter);
        }
        MaterialNumberPicker numberPicker = builder
                .build();
        return numberPicker;
    }

    public static AlertDialog.Builder createAlertDialog(Context context, String title) {
        return new AlertDialog.Builder(context)
                .setTitle(title);
    }
}
