package com.example.parcialpractico;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class SecondConnectivityAskService extends Service {
    public SecondConnectivityAskService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("onCreate: ", "Created Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            intent.setAction(MenuActivity.FILTER_ACTION_KEY);
            //Uso del servicio connectivity manager
            Log.d("Normal Service", "Funcionando");
            ConnectivityManager cm =
                    (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("isConnected", isConnected));
        }

        return START_STICKY;
    }
}