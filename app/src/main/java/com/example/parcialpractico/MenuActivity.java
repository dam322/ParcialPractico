package com.example.parcialpractico;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parcialpractico.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    boolean isConnected = true;
    MyReceiver myReceiver;
    public static final String FILTER_ACTION_KEY = "any_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        myReceiver = new MyReceiver();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_clases, R.id.nav_tareas, R.id.nav_cerrar)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    protected void onStart() {
        Intent tryService = new Intent(this, SecondConnectivityAskService.class);
        tryService.putExtra("isConnected", isConnected);
        startService(tryService);
        setReceiver();
        super.onStart();
    }

    public void IrRecursos(View  l){
        if(isConnected) {
            Log.d("Method", "IrRecursos: True");
        } else {
            Log.d("Method", "IrRecursos: False");
        }
        Intent ir = new Intent(getBaseContext(), Recursos.class);
        ir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ir);
    }
    public void irCalculo(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1BKlZbkNFdbzBSBEKPIMsUVFg3F5MxAze/view"));
        startActivity(i);
    }
    public void irIngenieria(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1z5hFzEskEG_fglggHGVIvH4O8v-ecPhg/view"));
        startActivity(i);
    }
    public void irDeporte(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/11wfCXs5UgryXu-uh7aBFbnFUsxX0C5No/view"));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

     //Metodo donde se instancia el BraodcastReceiver para recibir señales del intent enviado al Servicio
    private void setReceiver() {

        isConnected = myReceiver.getIsConnected();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }


    private static class MyReceiver extends BroadcastReceiver {
        String isConnectedString;
        private boolean isConnectedReceiver;

        public void setIsConnected(boolean x) {
            isConnectedReceiver = x;
        }

        public boolean getIsConnected() {
            return isConnectedReceiver;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            setIsConnected(intent.getBooleanExtra("isConnected", true));
            if(isConnectedReceiver) {
                isConnectedString = "true";
            } else {
                isConnectedString = "false";
                Toast.makeText(context.getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                //System.exit(0);
            }
            Log.d("IsConnectedReceiver", isConnectedString);
        }
    }
}