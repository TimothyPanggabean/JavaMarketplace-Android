package com.TimothyJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.request.LoginRequest;
import com.TimothyJmartKD.jmart_android.request.RegisterRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editName = findViewById(R.id.nameRegister);
        EditText editEmail = findViewById(R.id.emailRegister);
        EditText editPassword = findViewById(R.id.passwordRegister);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRequest newRegister = new RegisterRequest(
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editPassword.getText().toString(), new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent registerSuccess = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(registerSuccess);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),
                                        "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }
}