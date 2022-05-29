package com.example.parcialpractico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Recursos extends AppCompatActivity {

    MyReceiver myReceiver;
    static final String FILTER_ACTION_KEY = "any_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos);
        myReceiver = new MyReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent tryService = new Intent(this, ConnectivityAskService.class);
        startService(tryService);//Inicio del IntentService
        setReceiver();//Inicio del recibidor de señales del IntentService
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.item_back:
                irMenuActivity();
                return true;
            case R.id.item_cerrar:
                finish();
                irMainActivity();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void irMenuActivity() {
        Intent ir = new Intent(getBaseContext(), MenuActivity.class);
        ir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ir);
    }

    public void irNoticias(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://tulua.univalle.edu.co/noticias"));
        startActivity(i);
    }

    public void irConvocatoria(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://tulua.univalle.edu.co/36-convocatorias"));
        startActivity(i);
    }
    public void irBiblioteca(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://bibliotecadigital.univalle.edu.co/"));
        startActivity(i);
    }
    public void irBDE(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://biblioteca.univalle.edu.co/index.php/servicios/busquedas/libros-y-revistas-en-formato-electronico-a-c"));
        startActivity(i);
    }
    public void irGrupoInv(View view){
        Intent i = new Intent( Intent.ACTION_VIEW, Uri.parse("https://tulua.univalle.edu.co/investigacion"));
        startActivity(i);
    }

    public void irMainActivity() {
        Intent irMain = new Intent(getBaseContext(), MainActivity.class);
        irMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(irMain);
    }

    //Metodo donde se instancia el BraodcastReceiver para recibir señales del intent enviado al Servicio
    private void setReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    //clase privada que extiende de BroadcastReceiver, recibe la señal local provocada por el servicio
    //y obtiene un extra booleano que es enviado desde el servicio que da la señal si hay o no
    //conexion a internet
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
                //System.exit(0);
                Intent ir = new Intent(context.getApplicationContext(), MainActivity.class);
                ir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(ir);
                Toast.makeText(context.getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
            Log.d("IsConnectedReceive on RecursosActivity", isConnectedString);
        }
    }
}