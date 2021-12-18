package com.TimothyJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.request.AcceptInvoiceRequest;
import com.TimothyJmartKD.jmart_android.request.CancelInvoiceRequest;
import com.TimothyJmartKD.jmart_android.request.SubmitInvoiceRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Activity yang mengatur alur program pada store invoice detail page
 * yaitu: menampilakn detail mengenai sebuah invoice milik toko (penjualan)
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class StoreInvoiceDetailActivity extends AppCompatActivity {

    /**
     * Hal yang terjadi ketika activity dimulai
     * yaitu: menghubungkan ke halaman xml, inisasi tombol pada xml,
     * dan mengatur alur tiap tombol (apa yang terjadi ketika tombol ditekan)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_invoice_detail);
        Bundle bundle = getIntent().getExtras();

        Button accept = findViewById(R.id.acceptInvoiceButton);
        Button cancel = findViewById(R.id.cancelInvoiceButton);
        Button submit = findViewById(R.id.submitInvoiceButton);

        /**
         * Pilihan tombol yang tersedia tergantung status dari transaksi
         * Apabila waiting, maka hanya dapat di accept atau cancel
         * Apabila on prograss, maka hanya dapat di submit
         * Selain kedua kemungkinan itu, artinya transaksi tidak dapat diubah
         */
        if(bundle.getString("Message").equals("Waiting for confirmation")){
            submit.setVisibility(View.INVISIBLE);
            accept.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
        }
        else if(bundle.getString("Message").equals("On Progress")){
            submit.setVisibility(View.VISIBLE);
            accept.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
        }
        else{
            submit.setVisibility(View.INVISIBLE);
            accept.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
        }

        TextView productName = findViewById(R.id.productNameInvoice);
        TextView seller = findViewById(R.id.buyerInvoice);
        TextView status = findViewById(R.id.statusInvoice);

        /**
         * Menampilkan detail dari sebuah invoice
         * yaitu: nama, penjual, dan status message
         */
        productName.setText(bundle.getString("Name"));
        seller.setText(bundle.getString("Buyer"));
        status.setText(bundle.getString("Message"));

        /**
         * Alur yang terjadi ketika menekan tombol accept
         */
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
                            /**
                             * Ketika pesanan diaccept, user dikembalikan ke halaman InvoiceActivity
                             * Selain itu, tombol accept akan hilang dan tombol submit muncul
                             */
                            Toast.makeText(StoreInvoiceDetailActivity.this,
                                    "Products on progress", Toast.LENGTH_SHORT).show();

                            accept.setVisibility(View.INVISIBLE);
                            cancel.setVisibility(View.INVISIBLE);
                            submit.setVisibility(View.VISIBLE);

                            Intent intent = new Intent(StoreInvoiceDetailActivity.this, InvoiceActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StoreInvoiceDetailActivity.this,
                                "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                };
                /**
                 * Request untuk accept dikirimkan menggunakan AcceptInvoiceRequest
                 * diikuti dengan paymentId yang merupakan payment yang ingin diaccept
                 */
                AcceptInvoiceRequest acceptInvoiceRequest =
                        new AcceptInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(StoreInvoiceDetailActivity.this);
                queue.add(acceptInvoiceRequest);
            }
        });

        /**
         * Alur yang terjadi ketika menekan tombol cancel
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
                            /**
                             * Ketika pesanan dicancel, user dikembalikan ke halaman Invoice activity
                             * Selain itu, tombol cancel dan accept akan hilang
                             */
                            Toast.makeText(StoreInvoiceDetailActivity.this,
                                    "Products cancelled", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StoreInvoiceDetailActivity.this, InvoiceActivity.class);

                            accept.setVisibility(View.INVISIBLE);
                            cancel.setVisibility(View.INVISIBLE);

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StoreInvoiceDetailActivity.this,
                                "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                };
                /**
                 * Request untuk cancel dikirimkan menggunakan CancelInvoiceRequest
                 * diikuti dengan paymentId yang merupakan payment yang ingin dicancel
                 */
                CancelInvoiceRequest cancelInvoiceRequest =
                        new CancelInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(StoreInvoiceDetailActivity.this);
                queue.add(cancelInvoiceRequest);
            }
        });

        /**
         * Alur yang terjadi ketika menekan tombol cancel
         */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
                            /**
                             * Ketika pesanan dicancel, user dikembalikan ke halaman InvoiceActivity
                             * Selain itu, tombol submmit akan hilang
                             */
                            Toast.makeText(StoreInvoiceDetailActivity.this,
                                    "Products on delivery", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(StoreInvoiceDetailActivity.this, InvoiceActivity.class);

                            submit.setVisibility(View.INVISIBLE);

                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StoreInvoiceDetailActivity.this,
                                "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                };
                /**
                 * Request untuk cancel dikirimkan menggunakan SubmitInvoiceRequest
                 * diikuti dengan paymentId yang merupakan payment yang ingin disubmit
                 */
                SubmitInvoiceRequest submitInvoiceRequest =
                        new SubmitInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(StoreInvoiceDetailActivity.this);
                queue.add(submitInvoiceRequest);
            }
        });
    }
}