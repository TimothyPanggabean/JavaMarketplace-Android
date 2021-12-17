package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.annotation.Nullable;

import com.TimothyJmartKD.jmart_android.model.ProductPair;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SubmitInvoiceRequest extends StringRequest {
    ProductPair productPair;
    private static final String URL = "http://10.0.2.2:8080/payment/%d/submit";
    private final Map<String, String> params;

    public SubmitInvoiceRequest(int id, Response.Listener<String> listener,
                                @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL, id), listener, errorListener);
        params = new HashMap<>();
        params.put("receipt", "receipt");
    }
    public Map<String, String> getParams() {
        return params;
    }

}