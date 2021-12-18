package com.TimothyJmartKD.jmart_android.model;

/**
 * Model yang digunakan untuk mengatur komponen2 sebuah akun
 * yaitu: balance, email, name, password, store, dan regex (digunakan ketika membuat akun)
 */
public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Store store;
    public static final String REGEX_EMAIL="^\\w+([\\.]?[&\\*~\\w+])*@\\w+([\\.-]?)*(\\.\\w{2,3})+$";
    public static final String REGEX_PASSWORD="^(?=.*[0-9])(?=.*[a-z])(?=\\S+$)(?=.*[A-Z]).{8,}$";
}
