package com.TimothyJmartKD.jmart_android.request;

import androidx.annotation.Nullable;

import com.TimothyJmartKD.jmart_android.model.ProductCategory;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Request yang digunakan untuk mengambil list produk setelah difilter
 * Request diambil dari url ProductController.getProductFiltered pada kode backend
 */
public class GetFilteredProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/getFiltered?page=%d&pageSize=%d&accountId=%d&search=%s&minPrice=%d&maxPrice=%d&category=%s";

    /**
     * Constructor yang digunakan untuk menjalankan GET ke springboot
     * @param page nomor/index halaman
     * @param pageSize jumlah produk dalam 1 halaman
     * @param accountId id akun yang login (dikirim namun tidak digunakan pada backend)
     * @param search keyword yang dicari
     * @param minPrice harga minimum pada filter
     * @param maxPrice harga maksimum pada filter
     * @param category kategori produk pada filter
     * @param listener response listener (dipanggil ketika ada response)
     * @param errorListener response error listener (dipanggil ketika tidak ada response)
     */
    public GetFilteredProductRequest(int page, int pageSize, int accountId, String search, int minPrice,
                                     int maxPrice, ProductCategory category,
                                     Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.GET, String.format(URL, page, 10, accountId, search, minPrice, maxPrice, String.valueOf(category)), listener, errorListener);
    }
}