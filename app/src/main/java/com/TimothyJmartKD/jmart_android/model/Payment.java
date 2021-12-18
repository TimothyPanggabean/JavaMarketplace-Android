package com.TimothyJmartKD.jmart_android.model;

import java.util.*;

/**
 * Model yang digunakan untuk mengatur komponen2 dalam pembayaran
 * yaitu: jenis pengiriman, jumlah produk, dan history (timestamp dan status)
 *
 */
public class Payment extends Invoice
{
    public Shipment shipment;
    public int productCount;

    public ArrayList<Record> history;

    /**
     * Inisiasi komponen2 payment
     * @param buyerId id pembeli
     * @param productId id produk yang dibeli
     * @param productCount jumlah produk yang dibeli
     * @param shipment jenis pengiriman (diatur di model Shipment)
     */
    public Payment(int buyerId, int productId, int productCount, Shipment shipment)
    {
        super(buyerId,productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }

    /**
     * Mengambil harga total dari produk
     * @param product produk yang ingin dicari harga totalnya
     */
    public double getTotalPay(Product product)
    {
        return (product.price - (product.price * product.discount/100)) * productCount;
    }

    /**
     * Membuat record yang berisi informasi tambahan dari pembayaran
     * seperti: timestamp dan message (sama seperti status)
     */
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