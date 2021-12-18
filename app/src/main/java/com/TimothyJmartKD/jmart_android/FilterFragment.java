package com.TimothyJmartKD.jmart_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.model.ProductCategory;

/**
 * Activity yang mengatur alur program pada filter page
 * yaitu: memfilter produk yang ditampilkan berdasarkan filter yang ditentukan user,
 * serta meng-clear filter tersebut
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class FilterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static boolean isFiltered = false;
    public static String filterName;
    public static int filterLowestPrice, filterHighestPrice;
    public static boolean filterIsNew, filterIsUsed;
    public static ProductCategory filterCategory;

    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Inisiasi fragment, diikuti inisiasi pada onCreate
     */
    public static FilterFragment newInstance(String param1) {
        FilterFragment fragment = new FilterFragment();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        EditText name = v.findViewById(R.id.inputNameFilter);
        EditText lowestPrice = v.findViewById(R.id.inputLowestPriceFilter);
        EditText highestPrice = v.findViewById(R.id.inputHighestPriceFilter);

        CheckBox usedCondition = v.findViewById(R.id.checkBoxUsedFilter);
        CheckBox newCondition = v.findViewById(R.id.checkBoxNewFilter);

        Spinner productCategorySpinner = v.findViewById(R.id.spinnerFilter);

        Button clearButton = v.findViewById(R.id.buttonClearFilter);
        Button applyButton = v.findViewById(R.id.buttonApplyFilter);

        /**
         * Alur yang terjadi ketika menekan tombol apply
         * yaitu: mengambil informasi filter, dan mengubah nilai boolean isFiltered
         */
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFiltered = true;
                /**
                 * Memastikan bahwa semua field terisi sebelum informasinya dapat disimpan
                 */
                if(name.getText().toString().isEmpty()
                        || lowestPrice.getText().toString().isEmpty()
                        || highestPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Fields can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    filterName = name.getText().toString();
                    filterLowestPrice = Integer.parseInt(lowestPrice.getText().toString());
                    filterHighestPrice = Integer.parseInt(highestPrice.getText().toString());

                    if(usedCondition.isChecked()) {
                        filterIsUsed = true;
                    }
                    if(usedCondition.isChecked() == false) {
                        filterIsUsed = false;
                    }

                    if(newCondition.isChecked()) {
                        filterIsNew = true;
                    }
                    if(newCondition.isChecked() == false) {
                        filterIsNew = false;
                    }
                    filterCategory = ProductCategory.valueOf(productCategorySpinner.getSelectedItem().toString());

                    Toast.makeText(getActivity(), "Filter applied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Alur yang terjadi ketika menekan tombol clear,
         * yaitu: mengosongkan semua field dan mengubah nilai boolean isFiltered
         */
        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                isFiltered = false;

                name.getText().clear();
                lowestPrice.getText().clear();
                highestPrice.getText().clear();
                newCondition.setChecked(false);
                usedCondition.setChecked(false);
                productCategorySpinner.setSelection(0);

                Toast.makeText(getActivity(), "Filter cleared", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}