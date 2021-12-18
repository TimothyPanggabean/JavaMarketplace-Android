package com.TimothyJmartKD.jmart_android.request;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Request yang digunakan untuk mengirimkan register credential yang dimasukkan user
 * Request dikirim ke url AccountController.Register pada kode backend
 */
public class RegisterRequest extends StringRequest{
    private static final String URL =  "http://10.0.2.2:8080/account/register";
    private final Map<String, String> params;

    /**
     * Constructor yang digunakan untuk menjalankan POST ke springboot
     * Semua key diisi dengan value yang ditentukan pada HashMap params
     * @param name nama akun yang dimasukkan user
     * @param email email akun yang dimasukkan user
     * @param password password akun yang dimasukkan user
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public RegisterRequest(String name, String email, String password,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
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
