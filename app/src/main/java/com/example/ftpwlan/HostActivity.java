package com.example.ftpwlan;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Method;

public class HostActivity extends AppCompatActivity {
    private TextView wname;
    String TAG = "HostActivity";
    private WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);


        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        WifiConfiguration currentConfig=getWifiApConfiguration();
        if (currentConfig != null) {
            wname.setText("Name : "+currentConfig.SSID +"\n"+"Password : "+currentConfig.preSharedKey);
        }

        new BackHost().execute();

    }
    private WifiConfiguration getWifiApConfiguration() {
        try {
            Method method = wifiManager.getClass().getMethod("getWifiApConfiguration");
            return (WifiConfiguration) method.invoke(wifiManager);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}