package com.TimothyJmartKD.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Request yang digunakan untuk mengambil sebuah produk berdasarkan id
 * Request diambil dari url ProductController.getById (method implementasi dari basic get controller) pada kode backend
 * Request ini mengambil alih fungsi getById untuk produk pada RequestFactory
 */
public class GetSpecificProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/%d";

    /**
     * Constructor yang digunakan untuk menjalankan GET ke springboot
     * @param id id dari produk yang ingin diambil
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public GetSpecificProductRequest(int id, Response.Listener<String> listener,
                          @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, id), listener, errorListener);
    }
}