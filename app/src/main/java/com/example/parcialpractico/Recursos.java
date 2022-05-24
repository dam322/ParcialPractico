package com.example.parcialpractico;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Recursos extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos);
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
                return true;
            case R.id.item_cerrar:
                finish();
                System.exit(0);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
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

}