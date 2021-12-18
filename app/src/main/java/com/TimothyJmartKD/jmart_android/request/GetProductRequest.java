package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.annotation.Nullable;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Request yang digunakan untuk mengambil list produk
 * Request diambil dari url ProductController.getProductByStore pada kode backend
 * url memiliki komponen id akun, namun tidak digunakan di backend
 */
public class GetProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/" + getLoggedAccount().id + "/store?page=%d&pageSize=%d";

    /**
     * Constructor yang digunakan untuk menjalankan GET ke springboot
     * @param page nomor/index halaman
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     * angka 10 pada format menunjukkan pageSize (10 entri pada 1 page)
     */
    public GetProductRequest(Integer page, Response.Listener<String> listener,
                             @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 10), listener, errorListener);
    }
}