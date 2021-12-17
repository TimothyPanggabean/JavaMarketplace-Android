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

public class StoreInvoiceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_invoice_detail);
        Bundle bundle = getIntent().getExtras();

        Button accept = findViewById(R.id.acceptInvoiceButton);
        Button cancel = findViewById(R.id.cancelInvoiceButton);
        Button submit = findViewById(R.id.submitInvoiceButton);

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
        TextView seller = findViewById(R.id.sellerInvoice);
        TextView status = findViewById(R.id.statusInvoice);

        productName.setText(bundle.getString("Name"));
        seller.setText(bundle.getString("Seller"));
        status.setText(bundle.getString("Message"));

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
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
                AcceptInvoiceRequest acceptInvoiceRequest =
                        new AcceptInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(StoreInvoiceDetailActivity.this);
                queue.add(acceptInvoiceRequest);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
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
                CancelInvoiceRequest cancelInvoiceRequest =
                        new CancelInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(StoreInvoiceDetailActivity.this);
                queue.add(cancelInvoiceRequest);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
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
                SubmitInvoiceRequest submitInvoiceRequest =
                        new SubmitInvoiceRequest(bundle.getInt("PaymentId"), listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(StoreInvoiceDetailActivity.this);
                queue.add(submitInvoiceRequest);
            }
        });
    }
}