package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentRequest extends StringRequest {
    private static String id = String.valueOf(getLoggedAccount().id);
    private static final String URL = "http://10.0.2.2:8080/payment/create";
    private final Map<String, String> params;

    public PaymentRequest(int buyerId, int productId, int productCount,
                          String shipmentAddress, byte shipmentPlan, Response.Listener<String> listener,
                          Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId", String.valueOf(getLoggedAccount().id));
        params.put("productId", String.valueOf(productId));
        params.put("productCount", String.valueOf(productCount));
        params.put("shipmentAddress", String.valueOf(shipmentAddress));
        params.put("shipmentPlan", String.valueOf(shipmentPlan));
    }

    public Map<String, String> getParams() {
        return params;
    }
}