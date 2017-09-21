package com.zhouchatian.voicetotext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * Created by fujiayi on 2017/6/20.
 */

public abstract class ActivityCommon extends AppCompatActivity {

    private static final String TAG = "ActivityCommon";

    protected Button btn;
    protected TextView txtResult;

    protected Handler handler;


    protected int layout = R.layout.common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStrictMode();
        InFileStream.setContext(this);
        setContentView(layout);
        initView();
        handler = new Handler() {

            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handleMsg(msg);
            }

        };
        Logger.setHandler(handler);
        initPermission();
        initRecog();
    }


    protected abstract void initRecog();

    protected void handleMsg(Message msg) {
        if ( txtResult != null && msg.obj != null) {
            //此处可以同步显示
//            txtResult.append(msg.obj.toString() + "\n");
            Log.d(TAG, "handleMsg: "+msg.obj.toString() + "\n");
        }
    }

    protected void initView() {
        txtResult = (TextView) findViewById(R.id.txtResult);
        btn = (Button) findViewById(R.id.btn);
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm :permissions){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()){
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    private void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

    }
}
