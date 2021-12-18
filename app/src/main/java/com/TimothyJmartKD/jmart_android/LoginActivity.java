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
import com.TimothyJmartKD.jmart_android.model.Account;
import com.TimothyJmartKD.jmart_android.request.LoginRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity yang mengatur alur program pada login page
 * yaitu: menerima dan mengecek kredensial yang dimasukkan oleh user
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;
    private EditText password;
    private EditText email;
    private Button btnLogin;
    private TextView btnRegister;

    public static void setLoggedAccount(Account account) {
        loggedAccount = account;
    }

    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    /**
     * Hal yang terjadi ketika activity dimulai
     * yaitu: menghubungkan ke halaman xml, inisasi tombol pada xml,
     * dan mengatur alur tiap tombol (apa yang terjadi ketika tombol ditekan)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.passwordLogin);
        email = findViewById(R.id.emailLogin);
        btnLogin = findViewById(R.id.loginButton);
        btnRegister = findViewById(R.id.registerNowButton);

        /**
         * Alur yang terjadi ketika menekan tombol login,
         * yaitu: mengambil input dan mengirimkannya ke LoginRequest
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            /**
                             * Ketika login berhasil, user akan dipindahkan ke MainActivity
                             */
                            if(object != null){
                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            loggedAccount = gson.fromJson(object.toString(), Account.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                };
                String getemail = email.getText().toString().toLowerCase();
                String getpassword = password.getText().toString();
                LoginRequest loginRequest = new LoginRequest(getemail, getpassword, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        /**
         * Alur yang terjadi ketika menekan tombol register,
         * yaitu: pindah ke RegisterActivity
         */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}