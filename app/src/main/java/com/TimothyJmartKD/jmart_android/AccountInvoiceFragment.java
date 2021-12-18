package com.TimothyJmartKD.jmart_android;

import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.TimothyJmartKD.jmart_android.model.Account;
import com.TimothyJmartKD.jmart_android.model.Product;
import com.TimothyJmartKD.jmart_android.model.ProductPair;
import com.TimothyJmartKD.jmart_android.request.GetSpecificAccountRequest;
import com.TimothyJmartKD.jmart_android.request.GetSpecificProductRequest;
import com.TimothyJmartKD.jmart_android.request.InvoiceRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.model.Payment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity yang mengatur alur program pada account invoice page
 * yaitu: menampilkan preview sebuah invoice sebuah akun (pembelian)
 *
 * Activity ini memanfaatkan model ProductPair untuk mengambil informasi
 * tambahan dari sebuah payment seperti nama produk dan nama pembeli
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class AccountInvoiceFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AccountInvoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Inisiasi fragment, diikuti inisiasi pada onCreate
     */
    public static AccountInvoiceFragment newInstance(String param1) {
        AccountInvoiceFragment fragment = new AccountInvoiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Hal yang terjadi ketika fragment selesai diinisiasi
     * yaitu: menghubungkan ke halaman xml, inisasi tombol pada xml,
     * dan mengatur alur tiap tombol (apa yang terjadi ketika tombol ditekan)
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_invoice, container, false);
        ListView accountInvoiceList = v.findViewById(R.id.accountInvoiceList);
        Gson gson = new Gson();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<ProductPair> productPairList = new ArrayList<>();

                    /**
                     * Setiap komponen pada objek productPair diinisiasi,
                     * dengan urutan: payment, product, dan account
                     * Semuanya menggunakan response listener dan error listenernya masing2
                     */
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject newObj = jsonArray.getJSONObject(i);
                        Payment payment = gson.fromJson(newObj.toString(), Payment.class);
                        ProductPair productPair = new ProductPair();
                        productPair.payment = payment;

                        Response.Listener<String> listener1 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject newObj = null;
                                try {
                                    newObj = new JSONObject(response);
                                    Product product = gson.fromJson(newObj.toString(), Product.class);
                                    productPair.product = product;

                                    Response.Listener<String> listener2 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            JSONObject newObj = null;
                                            try {
                                                newObj = new JSONObject(response);
                                                Account account = gson.fromJson(newObj.toString(), Account.class);
                                                productPair.account = account;

                                                /**
                                                 * Karena page ini berfungsi untuk menampilkan pembelian dari akun saat ini,
                                                 * maka data hanya ditampilkan apabila buyerId payment nya sama dengan user id sekarang
                                                 */
                                                if (productPair.payment.buyerId == getLoggedAccount().id) {
                                                    productPairList.add(productPair);
                                                }

                                                /**
                                                 * Ketika semua komponen sudah diinisiasi, dikirimkan ke list view
                                                 * dengan menggunakan ArrayAdapter agar dapat ditampilkan
                                                 */
                                                ArrayAdapter<ProductPair> allItemsAdapter = new ArrayAdapter<ProductPair>(getActivity().getBaseContext(),
                                                        android.R.layout.simple_list_item_1, productPairList);
                                                accountInvoiceList.setAdapter(allItemsAdapter);

                                                /**
                                                 * Hal yang terjadi ketika menekan salah satu entry pada ListView
                                                 * Fragment akan mengirimkan bundle berisi informasi yang akan ditampilkan
                                                 * ataupun informasi lainnya yang akan digunakan pada detail activity nya
                                                 */
                                                accountInvoiceList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                                                        ProductPair productPair1 = productPairList.get(position);

                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("Name", productPair1.product.name);
                                                        bundle.putString("Seller", productPair1.account.store.name);
                                                        bundle.putString("Buyer", productPair1.account.name);
                                                        bundle.putString("Message",
                                                                String.valueOf(productPair1.payment.history.get(productPair1.payment.history.size()-1).message));
                                                        bundle.putInt("PaymentId", productPair1.payment.id);

                                                        Intent intent = new Intent(getContext(), AccountInvoiceDetailActivity.class);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                            catch (JSONException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getContext(), "An error occurred",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    };
                                    /**
                                     * Komponen account diinisiasi dengan menggunakan data yang didapat
                                     * dari GetSpecificAccountRequest yang diikuti accountId
                                     */
                                    GetSpecificAccountRequest getSpecificAccountRequest =
                                            new GetSpecificAccountRequest(product.accountId, listener2, errorListener);
                                    RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                                    queue.add(getSpecificAccountRequest);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "An error occurred",
                                        Toast.LENGTH_SHORT).show();
                            }
                        };
                        /**
                         * Komponen product diinisiasi dengan menggunakan data yang didapat
                         * dari GetSpecificProductRequest yang diikuti productId
                         */
                        GetSpecificProductRequest getSpecificProductRequest =
                                new GetSpecificProductRequest(payment.productId, listener1, errorListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                        queue.add(getSpecificProductRequest);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "An error occurred",
                        Toast.LENGTH_SHORT).show();
            }
        };
        /**
         * Komponen payment diinisiasi dengan menggunakan data yang didapat
         * dari InvoiceRequest
         */
        InvoiceRequest invoiceRequest = new InvoiceRequest(0, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        queue.add(invoiceRequest);

        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}