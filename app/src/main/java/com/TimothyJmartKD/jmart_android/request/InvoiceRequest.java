package com.TimothyJmartKD.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Request yang digunakan untuk mengambil list invoice
 * Request diambil dari url ProductController.getProductByStore pada kode backend
 * url memiliki komponen id akun, namun tidak digunakan di backend
 */
public class InvoiceRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/page?page=%d&pageSize=%d";
    /**
     * Constructor yang digunakan untuk menjalankan GET ke springboot
     * @param page nomor/index halaman
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     * angka 100 pada format menunjukkan pageSize (100 entri pada 1 page)
     */
    public InvoiceRequest(Integer page, Response.Listener<String> listener,
                          @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 100), listener, errorListener);
    }
}