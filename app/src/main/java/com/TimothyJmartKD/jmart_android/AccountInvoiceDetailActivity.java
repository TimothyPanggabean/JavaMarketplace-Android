package com.TimothyJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.model.Account;
import com.TimothyJmartKD.jmart_android.request.AcceptInvoiceRequest;
import com.TimothyJmartKD.jmart_android.request.CancelInvoiceRequest;
import com.TimothyJmartKD.jmart_android.request.GetProductRequest;
import com.TimothyJmartKD.jmart_android.request.SubmitInvoiceRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountInvoiceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invoice_detail);
        Bundle bundle = getIntent().getExtras();

        Button cancel = findViewById(R.id.cancelInvoiceButton);

        TextView productName = findViewById(R.id.productNameInvoice);
        TextView seller = findViewById(R.id.sellerInvoice);
        TextView status = findViewById(R.id.statusInvoice);

        if(bundle.getString("Message").equals("Waiting for confirmation"))
            cancel.setVisibility(View.VISIBLE);
        else cancel.setVisibility(View.INVISIBLE);

        productName.setText(bundle.getString("Name"));
        seller.setText(bundle.getString("Seller"));
        status.setText(bundle.getString("Message"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
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
                CancelInvoiceRequest cancelInvoiceRequest =
                        new CancelInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(AccountInvoiceDetailActivity.this);
                queue.add(cancelInvoiceRequest);
            }
        });
    }
}