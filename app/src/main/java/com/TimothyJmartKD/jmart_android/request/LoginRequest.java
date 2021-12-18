package com.TimothyJmartKD.jmart_android.request;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Request yang digunakan untuk mengirimkan login credential yang dimasukkan user
 * Request dikirim ke url AccountController.Login pada kode backend
 */
public class LoginRequest extends StringRequest {
    private static final String URL=  "http://10.0.2.2:8080/account/login";
    public final Map<String, String> params;

    /**
     * Constructor yang digunakan untuk menjalankan POST ke springboot
     * Semua key diisi dengan value yang ditentukan pada HashMap params
     * @param email email yang dimasukkan user
     * @param password password yang dimasukkan user
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public LoginRequest(String email, String password,
                        Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * Mengambil HashMap params yang telah diisi dengan value
     */
    public Map<String, String> getParams()
    {
        return params;
    }
}