package com.TimothyJmartKD.jmart_android.model;

/**
 * Model yang mengatur komponen2 sebuah store
 * yaitu: nama toko, alamat, nomor telepon toko, saldo toko, dan regex (digunakan ketika pembuatan toko)
 */
public class Store {
    public String name;
    public String address;
    public String phoneNumber;
    public static final String REGEX_PHONE =" ^[0-9]{9,12}\b";
    public static final String REGEX_NAME = "^[A-Z][a-z\\sa-z]{4,19}\b";
    public double balance;
}