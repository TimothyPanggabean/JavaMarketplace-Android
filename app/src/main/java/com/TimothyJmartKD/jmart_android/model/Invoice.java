package com.TimothyJmartKD.jmart_android.model;

import java.util.Date;

/**
 * Class Invoice
 *
 * Timothy Christian Panggabean
 * 1906355705
 */
public abstract class Invoice extends Serializable
{
    public final Date date;
    public int buyerId;
    public int productId;
    public int complaintId = -1;
    public Rating rating = Rating.NONE;

    protected Invoice(int buyerId, int productId)
    {
        this.buyerId = buyerId;
        this.productId = productId;
        date = new Date();
        rating = Rating.NONE;
        complaintId = -1;
    }

    public abstract double getTotalPay(Product product);

    public static enum Status
    {
        CANCELLED, COMPLAINT, DELIVERED, FAILED, FINISHED,
        ON_DELIVERY, ON_PROGRESS, WAITING_CONFIRMATION;
    }

    public static enum Rating
    {
        NONE, BAD, NEUTRAL, GOOD;
    }
}
