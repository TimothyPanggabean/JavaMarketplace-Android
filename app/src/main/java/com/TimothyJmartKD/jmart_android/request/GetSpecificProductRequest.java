package com.TimothyJmartKD.jmart_android.request;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class GetSpecificProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/%d";

    public GetSpecificProductRequest(int id, Response.Listener<String> listener,
                          @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, id), listener, errorListener);
    }
}