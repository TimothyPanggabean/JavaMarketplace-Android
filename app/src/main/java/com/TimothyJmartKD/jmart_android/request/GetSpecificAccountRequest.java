package com.TimothyJmartKD.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Request yang digunakan untuk mengambil sebuah akun berdasarkan id
 * Request diambil dari url AccountController.getById (method implementasi dari basic get controller) pada kode backend
 * Request ini mengambil alih fungsi getById untuk akun pada RequestFactory
 */
public class GetSpecificAccountRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/%d";

    /**
     * Constructor yang digunakan untuk menjalankan GET ke springboot
     * @param id id dari akun yang ingin diambil
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public GetSpecificAccountRequest(int id, Response.Listener<String> listener,
                                     @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, id), listener, errorListener);
    }
}
