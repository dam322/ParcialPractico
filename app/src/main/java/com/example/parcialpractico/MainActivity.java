package com.example.parcialpractico;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcialpractico.services.MyService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

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
    // google
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_ParcialPractico);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        textUser = findViewById(R.id.textUser);
        textPassword = findViewById(R.id.textPassword);
        tryText = findViewById(R.id.errorText);
        requestQueue = Volley.newRequestQueue(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), textUser.getText().toString(), Toast.LENGTH_SHORT).show();
                ArrayRequest();

                // moví esta linea para que se ejecute cundo se genera el request, porque es un
                // hilo, por ello en el momento de evaluar la bandera belogin

                if(beLogin){


                    Intent ir = new Intent(getBaseContext(), MenuActivity.class);

                    ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TASK | ir.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(ir);
                }

            }
        });
        // google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        //updateUI(account);
//
//    }

    // try use google siging
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //startActivityForResult(signInIntent, RC_SIGN_IN); // deprecated
        signInActivityResultLauncher.launch(signInIntent);

    }
    private ActivityResultLauncher<Intent> signInActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // -1 account signin
                // 0 no accoint
                // 1 account

                if (result.getResultCode() ==  -1 || result.getResultCode() == 1){
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                }
            }
    );
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            // en caso de sea necesario se pueden extraer más cosas
            //account.getEmail() //para email
            //account.getId() // id de google
            //account.getDisplayName() // nombre simplificado del usuario
            //account.getPhotoUrl() // uwl del avatar
            updateUI(account.getDisplayName());
            //updateUI(account);
        } catch (ApiException e) {
            tryText.setText("Lo sentimos, ha ocurrido un error :C\nIntenta más tarde!");
        }
    }

    private void ArrayRequest(){
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL1,
                null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONArray response) {
                        String name = textUser.getText().toString();
                        for(int i = 0; i<response.length(); i++){
                            try {
                                JSONObject jsonObject = new JSONObject(response.get(i).toString());
                                if(textUser.getText().toString().equals(jsonObject.getString("name")) && textPassword.getText().toString().equals(jsonObject.getString("password"))){
                                    //tryText.setText("Iniciar sesion ok!");
                                    beLogin = true;
                                    textPassword.setText("");
                                    textUser.setText("");
                                    break;
                                }else{
                                    tryText.setText("Credenciales incorrectas, por favor revise.");
                                    beLogin = false;
                                    textPassword.setText("");
                                    textUser.setText("");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(beLogin){
                            updateUI(name);
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

    private void updateUI(String name){
        Toast.makeText(getApplicationContext(), "Hola " + name +"!! XD  ", Toast.LENGTH_SHORT).show();
        Intent ir = new Intent(getBaseContext(), MenuActivity.class);
        ir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(ir);
    }

}