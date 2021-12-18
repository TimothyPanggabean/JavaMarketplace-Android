package com.TimothyJmartKD.jmart_android.model;

/**
 * Model yang digunakan untuk mengatur komponen2 sebuah produk
 * yaitu: akun penjual, kategori, kondisi, nama, berat, jenis pengiriman, dan kondisi (baru/tidak)
 */
public class Product extends Serializable{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    /**
     * Menampilkan informasi pada listview dari ProductFragment
     */
    public String toString()
    {
        return name;
    }
}
