package com.TimothyJmartKD.jmart_android.request;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/" + getLoggedAccount().id + "/store?page=%d&pageSize=%d";
    private final Map<String, String> params;

    public GetProductRequest(Integer page, Response.Listener<String> listener,
                             @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 10), listener, errorListener);
        params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(5));
    }

    public Map<String, String> getParams() {
        return params;
    }
}