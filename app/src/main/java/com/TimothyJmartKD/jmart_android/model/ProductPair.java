package com.TimothyJmartKD.jmart_android.model;

import android.widget.NumberPicker;

import com.TimothyJmartKD.R;

public class ProductPair {
    public Payment payment;
    public Product product;
    public Account account;

    public String toString()
    {
        return payment.date + " \n" + product.name + " (x" + payment.productCount + ") " + " from " + account.name;
    }
}
