package tw.yalan.cafeoffice.common;

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

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.grasea.grandroid.mvp.GrandroidActivity;

import butterknife.BindView;
import tw.yalan.cafeoffice.R;

/**
 * Created by Alan Ding on 2016/9/8.
 */
public class BaseActivity<P extends BasePresenter> extends GrandroidActivity<P> {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ProgressDialog pd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        getPresenter().onActivityCreate(getIntent().getExtras());
    }


    @Override
    public void onEventMainThread(UISettingEvent event) {
        //TODO 根據currentEvent內的參數，來決定Activity內的UI變化，機制類似於以前的UISetting
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onActivityResume();
    }

    @Override
    protected void onPause() {
        getPresenter().onActivityPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getPresenter().onActivityDestroy();
        super.onDestroy();
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Init views.
     */
    public void initViews() {

    }

    /**
     * Show Alert.
     *
     * @param message the message
     */
    protected void alert(String message) {
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton(R.string.alert_btn_confirm, null).show();
    }

    /**
     * Show Alert.
     *
     * @param message          the message
     * @param positiveListener the positive listener
     */
    protected void alert(String message, DialogInterface.OnClickListener positiveListener) {
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton(R.string.alert_btn_confirm, positiveListener).show();
    }

    /**
     * Show Alert.
     *
     * @param message          the message
     * @param positiveListener the positive listener
     * @param negativeListener the negative listener
     */
    protected void alert(String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton(R.string.alert_btn_confirm, positiveListener).setNegativeButton(R.string.alert_btn_cancel, negativeListener).show();
    }

    /**
     * Show Alert with view.
     *
     * @param v                the v
     * @param title            the title
     * @param message          the message
     * @param positiveListener the positive listener
     * @param negativeListener the negative listener
     */
    protected void alertWithView(View v, String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(this).setView(v)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_btn_confirm, positiveListener)
                .setNegativeButton(R.string.alert_btn_cancel, negativeListener).show();
    }

    /**
     * Show Toast.
     *
     * @param message the message
     */
    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Hide loading dialog.
     */
    @UiThread
    public void hideLoadingDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    /**
     * Show loading dialog.
     *
     * @param message the message
     */
    @UiThread
    public void showLoadingDialog(String message) {
        showLoadingDialog(message, false);
    }

    /**
     * Show loading dialog.
     *
     * @param message    the message
     * @param cancelable the cancelable
     */
    @UiThread
    public void showLoadingDialog(String message, Boolean cancelable) {
        if (pd != null && !pd.isShowing()) {
            if (message == null) {
                message = getString(R.string.msg_loading);
            }
            pd.setCancelable(cancelable);
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    onLoadingDialogCanceled();
                }
            });
            pd.setMessage(message);
            pd.show();
        }
    }

    /**
     * On loading dialog canceled.
     */
    public void onLoadingDialogCanceled() {
    }

    /**
     * Change to activity.
     *
     * @param activityClass the activity class
     * @param bundle        the bundle
     * @param flag          the flag
     */
    public void changeToActivity(Class activityClass, Bundle bundle, int flag) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flag != 0) {
            intent.setFlags(flag);
        }
        startActivity(intent);

    }

    /**
     * Change to activity.
     *
     * @param activityClass the activity class
     * @param bundle        the bundle
     */
    public void changeToActivity(Class activityClass, Bundle bundle) {
        changeToActivity(activityClass, bundle, 0);
    }

    /**
     * Change to activity for result.
     *
     * @param activityClass the activity class
     * @param bundle        the bundle
     * @param requestCode   the request code
     */
    public void changeToActivityForResult(Class activityClass, Bundle bundle, int requestCode) {
        changeToActivityForResult(activityClass, bundle, 0, requestCode);
    }

    /**
     * Change to activity for result.
     *
     * @param activityClass the activity class
     * @param bundle        the bundle
     * @param flag          the flag
     * @param requestCode   the request code
     */
    public void changeToActivityForResult(Class activityClass, Bundle bundle, int flag, int requestCode) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flag != 0) {
            intent.setFlags(flag);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * Network not found.
     */
    public void networkNotFound() {
        alert(getString(R.string.msg_network_not_found));
    }


    public void onRequestFailed(String type) {

    }
}
