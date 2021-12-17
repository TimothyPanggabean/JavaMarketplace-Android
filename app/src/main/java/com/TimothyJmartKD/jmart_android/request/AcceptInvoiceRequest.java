package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.annotation.Nullable;

import com.TimothyJmartKD.jmart_android.model.Payment;
import com.TimothyJmartKD.jmart_android.model.ProductPair;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AcceptInvoiceRequest extends StringRequest {
    ProductPair productPair;
    private static final String URL = "http://10.0.2.2:8080/payment/%d/accept";

    public AcceptInvoiceRequest(int id, Response.Listener<String> listener,
                          @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, String.format(URL, id), listener, errorListener);
    }
}