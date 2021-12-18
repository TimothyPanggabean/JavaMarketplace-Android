package com.TimothyJmartKD.jmart_android.request;

import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Request yang dilakukan ketika meng-cancel sebuah pesanan
 * Request dikirim ke url PaymentController.cancel di kode backend
 */
public class CancelInvoiceRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/%d/cancel";

    /**
     * Constructor yang menjalankan POST ke springboot
     * @param id id dari payment
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public CancelInvoiceRequest(int id, Response.Listener<String> listener,
                                @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL, id), listener, errorListener);
    }

}