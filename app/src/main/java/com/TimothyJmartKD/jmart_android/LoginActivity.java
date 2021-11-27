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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.TimothyJmartKD.jmart_android.model.Account;
import com.TimothyJmartKD.jmart_android.request.LoginRequest;

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editEmail = findViewById(R.id.emailLogin);
        EditText editPassword = findViewById(R.id.passwordLogin);
        Button loginButton = findViewById(R.id.loginButton);
        TextView registerLogin = findViewById(R.id.registerNowButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest newLogin = new LoginRequest(
                        editEmail.getText().toString(),
                        editPassword.getText().toString(), new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                        Intent loginSuccess = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loginSuccess);
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