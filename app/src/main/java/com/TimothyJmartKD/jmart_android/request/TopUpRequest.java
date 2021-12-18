package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Request yang dilakukan ketika melakukan top up saldo
 * Request dikirim ke url AccountController.topUp di kode backend
 */
public class TopUpRequest extends StringRequest {
    private final static String URL = "http://10.0.2.2:8080/account/" + getLoggedAccount().id + "/topUp";
    private final Map<String, String> params;

    /**
     * Constructor yang menjalankan POST ke springboot
     * Key balance diisi dengan value yang ditentukan pada HashMap params
     * @param amount jumlah saldo top up
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public TopUpRequest(Double amount, Response.Listener<String> listener,
                        Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("balance", String.valueOf(amount));
    }

    /**
     * Mengambil HashMap params yang telah diisi dengan value
     */
    public Map<String, String> getParams() {
        return params;
    }

}
