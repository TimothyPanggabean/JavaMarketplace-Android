package com.TimothyJmartKD.jmart_android.request;

import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Request yang dilakukan ketika meng-submit sebuah pesanan
 * Request dikirim ke url PaymentController.submit di kode backend
 */
public class SubmitInvoiceRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/%d/submit";
    private final Map<String, String> params;

    /**
     * Constructor yang menjalankan POST ke springboot
     * Key receipt diisi dengan value yang ditentukan pada HashMap params
     * @param id id dari payment
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public SubmitInvoiceRequest(int id, Response.Listener<String> listener,
                                @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL, id), listener, errorListener);
        params = new HashMap<>();
        params.put("receipt", "receipt");
    }

    /**
     * Mengambil HashMap params yang telah diisi dengan value
     */
    public Map<String, String> getParams() {
        return params;
    }
}