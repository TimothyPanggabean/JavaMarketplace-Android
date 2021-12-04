package com.TimothyJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.model.ProductCategory;
import com.TimothyJmartKD.jmart_android.request.CreateProductRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProductActivity extends AppCompatActivity {
    boolean conditionUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        EditText productName = findViewById(R.id.productName);
        EditText productWeight = findViewById(R.id.productWeight);
        EditText productPrice = findViewById(R.id.productPrice);
        EditText productDiscount = findViewById(R.id.productDiscount);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        Spinner shipmentSpinner = findViewById(R.id.shipmentSpinner);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button createButton = findViewById(R.id.createButton);

        categorySpinner.setAdapter(new ArrayAdapter<ProductCategory>
                (this, android.R.layout.simple_spinner_item, ProductCategory.values()));

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(getApplicationContext(), "Product created successfully", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Failed to create product", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                };

                String productNameString = productName.getText().toString();

                ProductCategory productCategory = ProductCategory.valueOf(categorySpinner.getSelectedItem().toString());
                byte productShipment = 1 << 0;

                switch(shipmentSpinner.getSelectedItem().toString()) {
                    case "INSTANT" :
                        productShipment = 1 << 0;
                        break;
                    case "SAME_DAY" :
                        productShipment = 1 << 1;
                        break;
                    case "NEXT DAY" :
                        productShipment = 1 << 2;
                        break;
                    case "REGULER" :
                        productShipment = 1 << 3;
                        break;
                    case "KARGO" :
                        productShipment = 1 << 4;
                        break;
                }
                if(productNameString.isEmpty() || productWeight.getText().toString().isEmpty() ||
                        productPrice.getText().toString().isEmpty() || productDiscount.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        int productWeightInt = Integer.parseInt(productWeight.getText().toString());
                        double productPriceDouble = Double.parseDouble(productPrice.getText().toString());
                        double productDiscountDouble = Double.parseDouble(productDiscount.getText().toString());
                        CreateProductRequest createProduct = new CreateProductRequest
                                (productNameString, productWeightInt, conditionUsed, productPriceDouble,
                                        productDiscountDouble, productCategory, productShipment,
                                        listener, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                        queue.add(createProduct);
                    }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.newCondition:
                        conditionUsed = false;
                        break;
                    case R.id.usedCondition:
                        conditionUsed = true;
                        break;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}