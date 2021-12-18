package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request yang digunakan ketika melakukan pembayaran
 * Request dikirim ke url AccountController.Login pada kode backend
 */
public class PaymentRequest extends StringRequest {
    private static String id = String.valueOf(getLoggedAccount().id);
    private static final String URL = "http://10.0.2.2:8080/payment/create";
    private final Map<String, String> params;

    /**
     * Constructor yang digunakan untuk menjalankan POST ke springboot
     * Semua key diisi dengan value yang ditentukan pada HashMap params
     * @param buyerId id dari user yang membeli produk
     * @param productId id produk yang dibeli
     * @param productCount jumlah produk yang dibeli
     * @param shipmentAddress alamat pengiriman
     * @param shipmentPlan jenis pengiriman
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public PaymentRequest(int buyerId, int productId, int productCount,
                          String shipmentAddress, byte shipmentPlan, Response.Listener<String> listener,
                          Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", String.valueOf(getLoggedAccount().id));
        params.put("productId", String.valueOf(productId));
        params.put("productCount", String.valueOf(productCount));
        params.put("shipmentAddress", String.valueOf(shipmentAddress));
        params.put("shipmentPlan", String.valueOf(shipmentPlan));
    }

    /**
     * Mengambil HashMap params yang telah diisi dengan value
     */
    public Map<String, String> getParams() {
        return params;
    }
}