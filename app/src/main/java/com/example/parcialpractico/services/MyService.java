package com.example.parcialpractico.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.parcialpractico.MainActivity;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"hola servicio", Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "SERVICIO", Toast.LENGTH_SHORT).show();
        conectividad obj= new conectividad();
        obj.execute();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public  class conectividad extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <5; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean is_wifi = false;
            boolean is_4g = false;
            NetworkInfo nwInfo = cm.getNetworkInfo(cm.getActiveNetwork());
            if(nwInfo.getType() == ConnectivityManager.TYPE_WIFI){
                Toast.makeText(getApplicationContext(), "I am Wifi", Toast.LENGTH_SHORT).show();
                Log.d("", " I am Wifi ");
                is_wifi = true;
            }else if (nwInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                is_4g = true;
                Toast.makeText(getApplicationContext(), "I am Mobile", Toast.LENGTH_SHORT).show();
                Log.d("", " I am Mobile");
            } else {
                Intent ir = new Intent(getBaseContext(), MainActivity.class);
                ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ir);
            }
        }
    }

}