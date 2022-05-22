package com.example.parcialpractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    EditText textUser, textPassword;
    TextView tryText;
    Boolean beLogin = false;
    RequestQueue requestQueue;
    private static final String URL1 = "https://628963f9e5e5a9ad3218cb51.mockapi.io/api/v1/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        textUser = findViewById(R.id.textUser);
        textPassword = findViewById(R.id.textPassword);
        tryText = findViewById(R.id.textView4);
        requestQueue = Volley.newRequestQueue(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), textUser.getText().toString(), Toast.LENGTH_SHORT).show();
                ArrayRequest();
                if(beLogin){
                    Intent ir = new Intent(getBaseContext(), SecondHome.class);
                    ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ir);
                }

            }
        });

    }

    private void ArrayRequest(){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL1,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                if(textUser.getText().toString().equals(jsonObject.getString("name")) && textPassword.getText().toString().equals(jsonObject.getString("password"))){
                                    tryText.setText("Iniciar sesion ok!");
                                    beLogin = true;
                                    break;
                                }else{
                                    tryText.setText("crendenciales no coinciden");
                                    beLogin = false;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(arrayRequest);
    }
}