package com.TimothyJmartKD.jmart_android.model;

import java.util.HashMap;

/**
 * Model yang mengatur increment id ketika ada data baru (akun/produk)
 */
public class Serializable {
    private static HashMap<Class<?>, Integer> mapCounter = new HashMap<>();
    public int id;
}