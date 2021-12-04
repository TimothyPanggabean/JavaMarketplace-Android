package com.TimothyJmartKD.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.TimothyJmartKD.jmart_android.model.ProductCategory;

import java.util.Map;

public class ProductRequest {
    private static final String CREATE_PRODUCT =
            "http://10.0.2.2:6969/product/create?accountId=%d&name=%s&weight=%d&price=%f&discount=%f&conditionUsed=%s&category=%s&shipmentPlans=%d";
    private static String parentURI = "product";
    private static String GET_BY_STORE = "/store?page=%d&pageSize=%d";

    public static StringRequest createProduct(
            int accountId,
            String name,
            int weight,
            double price,
            double discount,
            boolean conditionUsed,
            ProductCategory category,
            byte shipmentPlans,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ){
        String url = String.format(CREATE_PRODUCT, accountId, name, weight, price, discount, conditionUsed, category, shipmentPlans);
        return new StringRequest(Request.Method.POST, url, listener, errorListener);
    }

    public static StringRequest getProductByStore(
            int id,
            int page,
            int pageSize,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ){
        String url = RequestFactory.getURL().concat(GET_BY_STORE);
        url = String.format(url, parentURI, id, page, pageSize);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
}
