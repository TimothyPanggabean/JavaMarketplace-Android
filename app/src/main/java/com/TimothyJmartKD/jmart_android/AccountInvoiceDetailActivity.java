package com.TimothyJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.request.CancelInvoiceRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

/**
 * Activity yang mengatur alur program pada account invoice detail page
 * yaitu: menampilakn detail mengenai sebuah invoice milik akun (pembelian)
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class AccountInvoiceDetailActivity extends AppCompatActivity {

    /**
     * Hal yang terjadi ketika activity dimulai
     * yaitu: menghubungkan ke halaman xml, inisasi tombol pada xml,
     * dan mengatur alur tiap tombol (apa yang terjadi ketika tombol ditekan)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invoice_detail);
        /**
         * getBundle digunakan untuk penerimaan bundle dari AccountInvoiceActivity
         */
        Bundle bundle = getIntent().getExtras();

        Button cancel = findViewById(R.id.cancelInvoiceButton);

        TextView productName = findViewById(R.id.productNameInvoice);
        TextView seller = findViewById(R.id.sellerInvoice);
        TextView status = findViewById(R.id.statusInvoice);

        /**
         * Akun hanya bisa cancel pesanan yang sedang menunggu konfirmasi
         * oleh karena itu, tombol hanya muncul ketika string message sesuai
         */
        if(bundle.getString("Message").equals("Waiting for confirmation"))
            cancel.setVisibility(View.VISIBLE);
        else cancel.setVisibility(View.INVISIBLE);

        /**
         * Menampilkan detail dari sebuah invoice
         * yaitu: nama, penjual, dan status message
         */
        productName.setText(bundle.getString("Name"));
        seller.setText(bundle.getString("Seller"));
        status.setText(bundle.getString("Message"));

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
                             * Ketika pesanan dicancel, user dikembalikan ke halaman InvoiceActivity
                             */
                            Toast.makeText(AccountInvoiceDetailActivity.this,
                                    "Products cancelled", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AccountInvoiceDetailActivity.this, InvoiceActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AccountInvoiceDetailActivity.this,
                                "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                };
                /**
                 * Request untuk cancel dikirimkan menggunakan CancelInvoiceRequest
                 * diikuti dengan paymentId yang merupakan payment yang ingin dicancel
                 */
                CancelInvoiceRequest cancelInvoiceRequest =
                        new CancelInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(AccountInvoiceDetailActivity.this);
                queue.add(cancelInvoiceRequest);
            }
        });
    }
}