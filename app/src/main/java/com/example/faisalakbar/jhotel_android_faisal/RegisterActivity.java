package com.example.faisalakbar.jhotel_android_faisal;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText regFullName = (EditText) findViewById(R.id.inputRegFullName);
        final EditText regEmail = (EditText) findViewById(R.id.inputRegEmail);
        final EditText regPassword = (EditText) findViewById(R.id.inputRegPassword);
        final Button regButton = (Button) findViewById(R.id.buttonRegister);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullName = regFullName.getText().toString();
                final String email = regEmail.getText().toString();
                final String password = regPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse!=null){
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registration Success")
                                        .create()
                                        .show();
                            }
                        } catch(JSONException e){
                            AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Registration Failed")
                                    .create()
                                    .show();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(fullName,email,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}
