package com.example.parcialpractico;


import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
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


    public void IrRecursos(View  l){
        Intent ir = new Intent(getBaseContext(), Recursos.class);

        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
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
}