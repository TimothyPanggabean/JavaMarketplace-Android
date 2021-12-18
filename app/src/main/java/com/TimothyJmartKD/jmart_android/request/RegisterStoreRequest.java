package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Request yang digunakan untuk mengirimkan register credential untuk toko yang dimasukkan user
 * Request dikirim ke url AccountController.registerStore pada kode backend
 * url memiliki komponen id dari akun yang ingin register toko
 */
public class RegisterStoreRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/" + getLoggedAccount().id + "/registerStore";
    private final Map<String, String> params;

    /**
     * Constructor yang digunakan untuk menjalankan POST ke springboot
     * Semua key diisi dengan value yang ditentukan pada HashMap params
     * @param name nama toko yang diregister
     * @param address alamat toko yang diregister
     * @param phoneNumber nomor telepon toko yang diregister
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public RegisterStoreRequest(String name, String address, String phoneNumber, Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }

    /**
     * Mengambil HashMap params yang telah diisi dengan value
     */
    public Map<String, String> getParams() {
        return params;
    }
}