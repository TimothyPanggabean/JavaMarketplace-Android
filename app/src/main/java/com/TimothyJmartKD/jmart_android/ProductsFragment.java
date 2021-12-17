package com.TimothyJmartKD.jmart_android;

import static com.TimothyJmartKD.jmart_android.FilterFragment.isFiltered;
import static com.TimothyJmartKD.jmart_android.LoginActivity.getLoggedAccount;
import static com.TimothyJmartKD.jmart_android.FilterFragment.filterCategory;
import static com.TimothyJmartKD.jmart_android.FilterFragment.filterHighestPrice;
import static com.TimothyJmartKD.jmart_android.FilterFragment.filterLowestPrice;
import static com.TimothyJmartKD.jmart_android.FilterFragment.filterName;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.model.Product;
import com.TimothyJmartKD.jmart_android.model.ProductCategory;
import com.TimothyJmartKD.jmart_android.request.GetFilteredProductRequest;
import com.TimothyJmartKD.jmart_android.request.GetProductRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static List<Product> productReturned = new ArrayList<>();

    private String mParam1;
    private String mParam2;

    private Button prevPageBtn, nextPageBtn, goFindProductBtn;
    private EditText pageNum;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance(String param1) {
        ProductsFragment fragment = new ProductsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);
        ListView lstItems = v.findViewById(R.id.productsList);
        Gson gson = new Gson();

        prevPageBtn = v.findViewById(R.id.productPrevPage);
        nextPageBtn = v.findViewById(R.id.productNextPage);
        goFindProductBtn = v.findViewById(R.id.productGoPage);
        pageNum = v.findViewById(R.id.productPageNumber);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    productReturned.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject newObj = jsonArray.getJSONObject(i);
                        Product product = gson.fromJson(newObj.toString(), Product.class);
                        productReturned.add(product);
                    }
                    ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<Product>(getActivity().getBaseContext(),
                            android.R.layout.simple_list_item_1, productReturned);
                    lstItems.setAdapter(allItemsAdapter);
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
        int pages = Integer.parseInt(pageNum.getText().toString()) - 1;

        GetProductRequest newGetProduct = new GetProductRequest(pages, listener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
        queue.add(newGetProduct);

        lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Product product = productReturned.get(position);

                for(Product preProduct: productReturned){
                    if(preProduct.name.equals(((TextView)view).getText().toString()))
                        product = preProduct;
                }

                Bundle bundle = new Bundle();
                bundle.putString("Name", product.name);
                bundle.putInt("Weight", product.weight);
                bundle.putBoolean("ConditionUsed", product.conditionUsed);
                bundle.putDouble("Price", product.price);
                bundle.putDouble("Discount", product.discount);
                bundle.putString("Category", product.category.toString());
                bundle.putByte("ShipmentPlans", product.shipmentPlans);
                bundle.putInt("ProductId", product.id);
                bundle.putInt("AccountId",product.accountId);

                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        prevPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = Integer.parseInt(pageNum.getText().toString());
                if(currentPage == 1) {
                    Toast.makeText(getContext(), "That page doesn't exist", Toast.LENGTH_SHORT).show();
                }
                else {
                    currentPage--;
                    pageNum.setText(String.valueOf(currentPage));
                }
            }
        });

        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = Integer.parseInt(pageNum.getText().toString());
                currentPage++;
                pageNum.setText(String.valueOf(currentPage));
            }
        });

        goFindProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFiltered) {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                productReturned.clear();

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject newObj = jsonArray.getJSONObject(i);
                                    Product product = gson.fromJson(newObj.toString(), Product.class);
                                    productReturned.add(product);
                                }

                                ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<Product>(getActivity().getBaseContext(),
                                        android.R.layout.simple_list_item_1,
                                        productReturned);
                                lstItems.setAdapter(allItemsAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "That page doesn't exist",
                                    Toast.LENGTH_SHORT).show();
                        }
                    };
                    int pages = Integer.parseInt(pageNum.getText().toString()) - 1;

                    GetProductRequest newGetProduct = new GetProductRequest(pages, listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                    queue.add(newGetProduct);
                }
                else{
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            productReturned.clear();

                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject newObj = jsonArray.getJSONObject(i);
                                Product product = gson.fromJson(newObj.toString(), Product.class);
                                productReturned.add(product);
                            }
                                ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<Product>(getActivity().getBaseContext(),
                                        android.R.layout.simple_list_item_1,
                                        productReturned);
                                        lstItems.setAdapter(allItemsAdapter);
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
                int pages = Integer.parseInt(pageNum.getText().toString()) - 1;

                    String searchName = filterName;
                    int minimumPrice = filterLowestPrice;
                    int maximumPrice = filterHighestPrice;
                    ProductCategory category = filterCategory;

                    GetFilteredProductRequest newFilteredProduct = new GetFilteredProductRequest(pages,
                            getLoggedAccount().id, searchName, minimumPrice, maximumPrice, category, listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
                    queue.add(newFilteredProduct);
            }
            }
        });
        return v;
    }

    public void onResume() {
        super.onResume();
        Gson gson = new Gson();
        ListView listItems = getView().findViewById(R.id.productsList);

        if (!isFiltered) {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        productReturned.clear();

                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Product product = gson.fromJson(obj.toString(), Product.class);
                            productReturned.add(product);
                        }

                        ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productReturned);
                        listItems.setAdapter(allItemsAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            };

            int pages = Integer.parseInt(pageNum.getText().toString()) - 1;
            GetProductRequest newGetProduct = new GetProductRequest(pages, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
            queue.add(newGetProduct);
        } else {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        productReturned.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Product product = gson.fromJson(obj.toString(), Product.class);
                            productReturned.add(product);
                        }
                        ArrayAdapter<Product> allItemsAdapter = new ArrayAdapter<Product>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, productReturned);
                        listItems.setAdapter(allItemsAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            };

            int pages = Integer.parseInt(pageNum.getText().toString()) - 1;
            String searchName = filterName;
            int minPrice = filterLowestPrice;
            int maxPrice = filterHighestPrice;
            ProductCategory category = filterCategory;

            GetFilteredProductRequest newFilteredProduct = new GetFilteredProductRequest(pages, getLoggedAccount().id, searchName, minPrice, maxPrice, category, listener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
            queue.add(newFilteredProduct);
        }
    }
}