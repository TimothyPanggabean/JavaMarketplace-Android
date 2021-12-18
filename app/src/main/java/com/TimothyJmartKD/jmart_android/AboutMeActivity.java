package com.TimothyJmartKD.jmart_android;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.model.Store;
import com.TimothyJmartKD.jmart_android.request.RegisterStoreRequest;
import com.TimothyJmartKD.jmart_android.request.TopUpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Activity yang mengatur alur program pada about me page
 * yaitu: menampilkan informasi user, toko, membuat toko, dan top up
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class AboutMeActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();

    /**
     * Hal yang terjadi ketika activity dimulai
     * yaitu: menghubungkan ke halaman xml, inisasi tombol pada xml,
     * dan mengatur alur tiap tombol (apa yang terjadi ketika tombol ditekan)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        this.setTitle("About Me");

        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);

        TextView accountName = findViewById(R.id.accountName);
        TextView accountEmail = findViewById(R.id.accountEmail);
        TextView accountBalance = findViewById(R.id.accountBalance);
        EditText topUpAmount = findViewById(R.id.topUpAmount);

        /**
         * Menampilkan informasi dasar mengenai akun
         * yaitu: nama, email, dan balance
         */
        accountName.setText(String.valueOf(getLoggedAccount().name));
        accountEmail.setText(String.valueOf(getLoggedAccount().email));
        accountBalance.setText((formater.format(getLoggedAccount().balance)));

        CardView storeCard = findViewById(R.id.store_card);
        CardView registeredCard = findViewById(R.id.registeredCard);

        Button registerStoreButton = findViewById(R.id.register_store);
        Button registerButton = findViewById(R.id.register_button);
        Button cancelButton = findViewById(R.id.cancel_button);
        Button topUpButton = findViewById(R.id.topUpButton);

        TextView registeredName = findViewById(R.id.registeredName);
        TextView registeredAddress = findViewById(R.id.registeredAddress);
        TextView registeredPhoneNumber = findViewById(R.id.registeredPhoneNumber);

        TextView registerName = findViewById(R.id.registerName);
        TextView registerAddress = findViewById(R.id.registerAddress);
        TextView registerPhoneNumber = findViewById(R.id.registerPhoneNumber);

        /**
         * Membuat tampilan yang berbeda ketika sudah punya toko dan belum
         * Ketika ada, informasi toko ditampilkan
         * Ketika tidak, tampilkan opsi untuk membuat toko baru
         */
        if(getLoggedAccount().store != null) {
            registerStoreButton.setVisibility(View.GONE);
            storeCard.setVisibility(View.GONE);
            registeredCard.setVisibility(View.VISIBLE);

            registeredName.setText(getLoggedAccount().store.name);
            registeredAddress.setText(getLoggedAccount().store.address);
            registeredPhoneNumber.setText(getLoggedAccount().store.phoneNumber);
        }
        else {
            registerStoreButton.setVisibility(View.VISIBLE);
            storeCard.setVisibility(View.GONE);
            registeredCard.setVisibility(View.GONE);
        }

        /**
         * Alur yang terjadi ketika menekan tombol topUp
         */
        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        if(response.equals("true"))
                        {
                            /**
                             * User hanya bisa topup apabila sudah memiliki toko
                             * ketika berhasil, informasi balance akan di update
                             */
                            if(getLoggedAccount().store!=null) {
                                Double totalBalance = getLoggedAccount().balance
                                        + Double.parseDouble(topUpAmount.getText().toString());
                                accountBalance.setText(String.valueOf(formater.format(totalBalance)));
                                getLoggedAccount().balance = totalBalance;

                                topUpAmount.setText("");

                                Toast.makeText(getApplicationContext(),
                                        "Top up successful", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getApplicationContext(),
                                    "Need a store to top up", Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                                Toast.makeText(getApplicationContext(),
                                    "Top up failed", Toast.LENGTH_SHORT).show();
                            }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(),
                                "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                };

                /**
                 * Request hanya akan dijalankan ketika:
                 * jumlah top up tidak kosong,
                 * akun yang top up sudah memiliki store,
                 * dan nominal top up minimal Rp 20.000
                 */
                if(topUpAmount.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Field can't be empty",
                            Toast.LENGTH_SHORT).show();
                }
                else if(getLoggedAccount().store==null)
                    Toast.makeText(getApplicationContext(), "Need a store to top up",
                            Toast.LENGTH_SHORT).show();
                else
                    {
                    Double amount = Double.valueOf(topUpAmount.getText().toString());

                    if(amount < 20000)
                    {
                        Toast.makeText(getApplicationContext(), "The minimum amount is Rp20.000",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            TopUpRequest newTopUp = new TopUpRequest(amount, listener, errorListener);
                            RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                            queue.add(newTopUp);
                        }
                }
            }
        });

        /**
         * Alur yang terjadi ketika menekan tombol register store
         * CardView baru akan tampil untuk tempat mengisi informasi toko yang ingin diregister
         * CardView ini berisi:
         * field informasi (store name, address, phone number)
         * tombol cancel (menutup cardview yang baru dibuat)
         * dan tombol register (mengirimkan RegisterStoreRequest)
         */
        registerStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeCard.setVisibility(View.VISIBLE);
                registerStoreButton.setVisibility(View.INVISIBLE);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        registerStoreButton.setVisibility(View.VISIBLE);
                        storeCard.setVisibility(View.INVISIBLE);
                    }
                });

                registerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Response.Listener<String> listener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        if (object != null) {

                                            /**
                                             * Ketika store berhasil di register, informasi pada card akan berubah
                                             * sesuai informasi yang diisi
                                             */
                                            getLoggedAccount().store = gson.fromJson(object.toString(), Store.class);

                                            registeredName.setText(getLoggedAccount().store.name);
                                            registeredAddress.setText(getLoggedAccount().store.address);
                                            registeredPhoneNumber.setText(getLoggedAccount().store.phoneNumber);
                                            Toast.makeText(getApplicationContext(), "Store registered successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Store registration failed", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        };

                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        };

                        /**
                         * Sebelum mengirimkan request, field dicek terlebih dahulu
                         * Apabila salah satu saja kosong, request tidak dikirimkan dan
                         * register akan gagal
                         */
                        String newStoreName = registerName.getText().toString();
                        String newStoreAddress = registerAddress.getText().toString();
                        String newStorePhoneNumber = registerPhoneNumber.getText().toString();

                        if(newStoreName.isEmpty() || newStoreAddress.isEmpty() || newStorePhoneNumber.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            RegisterStoreRequest newRegisterStore = new RegisterStoreRequest(newStoreName, newStoreAddress, newStorePhoneNumber,
                                    listener, errorListener);

                            queue.add(newRegisterStore);

                            storeCard.setVisibility(View.GONE);
                            registeredCard.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
}