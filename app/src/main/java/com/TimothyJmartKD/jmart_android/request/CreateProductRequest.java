package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.TimothyJmartKD.jmart_android.model.ProductCategory;
import java.util.HashMap;
import java.util.Map;

/**
 * Request yang digunakan ketika membuat produk
 * Request dikirim ke url ProductController.create di kode backend
 */
public class CreateProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/create";
    private final Map<String, String> params;

    /**
     * Constructor yang digunakan untuk menjalankan POST ke springboot
     * Semua key diisi dengan value yang ditentukan pada HashMap params
     * @param name nama produk yang dibuat
     * @param weight berat produk yang dibuat
     * @param conditionUsed kondisi produk yang dibuat (baru/bekas)
     * @param price harga produk yang dibuat
     * @param discount diskon produk yang dibuat
     * @param category kategori produk yang dibuat
     * @param shipmentPlans jenis pengiriman produk yang dibuat
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public CreateProductRequest(String name, int weight, boolean conditionUsed, double price,
                                double discount, ProductCategory category, byte shipmentPlans,
                                Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", String.valueOf(getLoggedAccount().id));
        params.put("name", name);
        params.put("weight", String.valueOf(weight));
        params.put("conditionUsed", String.valueOf(conditionUsed));
        params.put("price", String.valueOf(price));
        params.put("discount", String.valueOf(discount));
        params.put("category", String.valueOf(category));
        params.put("shipmentPlans", String.valueOf(shipmentPlans));
    }

    /**
     * Mengambil HashMap params yang telah diisi dengan value
     */
    public Map<String, String> getParams() {
        return params;
    }
}