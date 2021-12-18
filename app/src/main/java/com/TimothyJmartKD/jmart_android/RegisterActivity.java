package com.TimothyJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.request.RegisterRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity yang mengatur alur program pada register page
 * yaitu: menerima kredensial yang dimasukkan oleh user untuk membuat akun baru
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class RegisterActivity extends AppCompatActivity{
    private EditText NameRegister;
    private EditText EmailRegister;
    private EditText PasswordRegister;
    private Button btnRegister;

    /**
     * Hal yang terjadi ketika activity dimulai
     * yaitu: menghubungkan ke halaman xml, inisasi tombol pada xml,
     * dan mengatur alur tiap tombol (apa yang terjadi ketika tombol ditekan)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        NameRegister = findViewById(R.id.nameRegister);
        EmailRegister = findViewById(R.id.emailRegister);
        PasswordRegister = findViewById(R.id.passwordRegister);
        btnRegister = findViewById(R.id.registerButton);

        /**
         * Alur yang terjadi ketika menekan tombol login,
         * yaitu: mengambil input dan mengirimkannya ke RegisterRequest
         */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            /**
                             * Ketika register berhasil, user akan dipindahkan ke MainActivity
                             */
                            if(object != null){
                                Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                };
                String name = NameRegister.getText().toString();
                String email = EmailRegister.getText().toString().toLowerCase();
                String password = PasswordRegister.getText().toString();
                RegisterRequest registerRequest = new RegisterRequest(name, email, password, listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}