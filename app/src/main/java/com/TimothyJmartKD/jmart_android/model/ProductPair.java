package com.TimothyJmartKD.jmart_android.model;

/**
 * Model yang menghubungkan payment dengan produk dan akunnya
 * produk digunakan untuk mengambil nama produk (di payment hanya ada id produk)
 * akun digunakan untuk mengambil nama penjual/pembeli (di payment tidak ada informasi ini)
 */
public class ProductPair {
    public Payment payment;
    public Product product;
    public Account account;

    /**
     * Menampilkan informasi pada listview di AccountInvoiceFragment dan StoreInvoiceFragment
     */
    public String toString()
    {
        return payment.date + " \n" + product.name + " (x" + payment.productCount + ") " + " from " + account.name;
    }
}
