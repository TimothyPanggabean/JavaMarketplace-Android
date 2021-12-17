package com.TimothyJmartKD.jmart_android.model;

import java.util.*;

/**
 * Class Payment
 *
 * Timothy Christian Panggabean
 * 1906355705
 */
public class Payment extends Invoice
{
    public Shipment shipment;
    public int productCount;

    public ArrayList<Record> history;

    public Payment(int buyerId, int productId, int productCount, Shipment shipment)
    {
        super(buyerId,productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }

    public double getTotalPay(Product product)
    {
        return product.price - (product.price * product.discount);
    }

    public static class Record
    {
        public final Date date;
        public String message;
        public Status status;

        public Record(Status status, String message)
        {
            date = new Date();
        }
    }
}