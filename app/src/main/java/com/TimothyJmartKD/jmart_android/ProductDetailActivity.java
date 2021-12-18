package com.TimothyJmartKD.jmart_android;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.request.PaymentRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Activity yang mengatur alur program pada product detail page
 * yaitu: menampilakn detail mengenai sebuah produk, dan membelinya
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class ProductDetailActivity extends AppCompatActivity {

    /**
     * Hal yang terjadi ketika activity dimulai
     * yaitu: menghubungkan ke halaman xml, inisasi tombol pada xml,
     * dan mengatur alur tiap tombol (apa yang terjadi ketika tombol ditekan)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        CardView productDetail = findViewById(R.id.productDetailsCard);
        CardView confirmation = findViewById(R.id.confirmationCard);
        confirmation.setVisibility(View.INVISIBLE);

        /**
         * Mengatur nilai minimal dan maksimal dari number picker
         */
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(20);

        /**
         * Mengatur jenis format yang dilakukan,
         * yaitu: mengubah balance ke dalam format rupiah
         */
        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        TextView productName = findViewById(R.id.productNameText);
        TextView productWeight = findViewById(R.id.productWeightText);
        TextView productConditionUsed = findViewById(R.id.productConditionUsedText);
        TextView productPrice = findViewById(R.id.productPriceText);
        TextView productDiscount = findViewById(R.id.productDiscountText);
        TextView productCategory = findViewById(R.id.productCategoryText);
        TextView productShipmentPlans = findViewById(R.id.productShipmentPlansText);
        TextView nameConfirmation = findViewById(R.id.productNameText2);
        TextView amountConfirmation = findViewById(R.id.productAmount);
        TextView totalPrice = findViewById(R.id.totalPrice);
        TextView total = findViewById(R.id.totalText);

        Button accept = findViewById(R.id.acceptButton);
        Button cancel = findViewById(R.id.cancelButton2);
        Button buyButton = findViewById(R.id.buyButton);

        NumberPicker numberPicker = findViewById(R.id.numberPicker);

        /**
         * Mengambil data bundle yang dikirim oleh ProductsFragment
         */
        Bundle bundle = getIntent().getExtras();

        buyButton.setVisibility(View.INVISIBLE);
        numberPicker.setVisibility(View.INVISIBLE);
        total.setVisibility(View.INVISIBLE);

        /**
         * Mengisi setiap informasi dari produk yang terkait
         */
        productName.setText(bundle.getString("Name"));
        productWeight.setText(String.valueOf(bundle.getInt("Weight")) + " kg");

        if(bundle.getBoolean("ConditionUsed") == false)
            productConditionUsed.setText("New");
        else productConditionUsed.setText("Used");

        productPrice.setText(String.valueOf(formater.format(bundle.getDouble("Price"))));
        productDiscount.setText(String.valueOf(bundle.getDouble("Discount")) + "%");
        productCategory.setText(bundle.getString("Category"));

        if(bundle.getByte("ShipmentPlans") == 1)
            productShipmentPlans.setText("Instant");
        else if(bundle.getByte("ShipmentPlans") == 2)
            productShipmentPlans.setText("Same Day");
        else if(bundle.getByte("ShipmentPlans") == 4)
            productShipmentPlans.setText("Next Day");
        else if(bundle.getByte("ShipmentPlans") == 8)
            productShipmentPlans.setText("Reguler");
        else productShipmentPlans.setText("Kargo");

        /**
         * Tombol pilihan hanya akan tampil apabila produk tidak berasal
         * dari toko milik user saat ini
         */
        if(getLoggedAccount().id == bundle.getInt("AccountId"))
        {
            buyButton.setVisibility(View.INVISIBLE);
            numberPicker.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
        }
        else {
            buyButton.setVisibility(View.VISIBLE);
            numberPicker.setVisibility(View.VISIBLE);
            total.setVisibility(View.VISIBLE);
        }

        /**
         * Alur yang terjadi ketika menekan tombol buy,
         * yaitu: memunculkan CardView konfirmasi yang berisi informasi
         * produk seperti nama, jumlah yang dibeli, dan harga total
         */
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double productPrice = bundle.getDouble("Price");
                int amount = numberPicker.getValue();
                Double discount = bundle.getDouble("Discount");
                Double discountedPrice = productPrice - (productPrice * discount/100);
                Double grandTotal = discountedPrice * amount;

                if(getLoggedAccount().balance < grandTotal)
                {
                    /**
                     * Pembelian hanya bisa dilakukan apabila balance user cukup
                     */
                    Toast.makeText(ProductDetailActivity.this,
                            "Insufficient funds", Toast.LENGTH_SHORT).show();
                }
                else {
                    confirmation.setVisibility(View.VISIBLE);
                    nameConfirmation.setText(bundle.getString("Name"));
                    amountConfirmation.setText(String.valueOf(numberPicker.getValue()));
                    totalPrice.setText(String.valueOf(formater.format(grandTotal)));
                }
            }
        });

        /**
         * Alur yang terjadi ketika menekan tombol accept,
         * yaitu: mengurangi balance user sesuai harga pembelian, dan
         * membuat invoice baru dengan status awaiting confirmation
         */
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation.setVisibility(View.INVISIBLE);

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Double productPrice = bundle.getDouble("Price");
                                int amount = numberPicker.getValue();
                                Double discount = bundle.getDouble("Discount");
                                Double discountedPrice = productPrice - (productPrice * discount/100);
                                Double grandTotal = discountedPrice * amount;

                                getLoggedAccount().balance -= grandTotal;

                                Toast.makeText(ProductDetailActivity.this,
                                        "Products awaiting confirmation", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductDetailActivity.this, "Purchase failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductDetailActivity.this, " An error occurred", Toast.LENGTH_SHORT).show();
                    }
                };
                /**
                 * Mengirimkan request ke PaymentRequest menggunakan informasi mengenai pembelian user
                 */
                int buyerId = getLoggedAccount().id;
                int productId = bundle.getInt("ProductId");
                int productCount = numberPicker.getValue();
                String shipmentAddress = getLoggedAccount().store.address;
                byte shipmentPlan = bundle.getByte("ShipmentPlans");
                PaymentRequest paymentRequest = new PaymentRequest(buyerId, productId, productCount, shipmentAddress, shipmentPlan,
                        listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(ProductDetailActivity.this);
                queue.add(paymentRequest);
            }
        });

        /**
         * Alur yang terjadi ketika menekan tombol cancel,
         * yaitu: menghapus CardView confirmation
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation.setVisibility(View.INVISIBLE);
            }
        });
    }
}