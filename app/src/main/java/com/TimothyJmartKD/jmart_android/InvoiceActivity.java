package com.TimothyJmartKD.jmart_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.TimothyJmartKD.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Activity yang menjadi main activity untuk invoice page
 * yaitu: inisiasi kedua fragment dalam activity invoice
 *
 * Activity ini menggunakan 2 fragment,
 * yaitu: AccountInvoiceFragment dan StoreInvoiceFragment
 * Beberapa method pertama berfungsi untuk inisiasi kedua fragment ini
 *
 * Activity ini memanfaatkan response listener dan error response listener,
 * dimana response listener dijalankan ketika menerima response dari springboot
 * dan response error listener ketika tidak menerima response dari springboot
 */
public class InvoiceActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    //The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    public static ViewPager2 viewPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private FragmentStateAdapter pagerAdapter;
    // Arrey of strings FOR TABS TITLES
    private String[] titles = new String[]{"Account", "Store"};
    // tab titles

    /**
     * Hal yang terjadi ketika activity dimulai
     * yaitu: menghubungkan ke halaman xml dan mencari komponen yang ada pada xml
     * dengan menggunakan id nya
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Invoice");
        viewPager = findViewById(R.id.mypager2);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter); //inflating tab layout
        TabLayout tabLayout =( TabLayout) findViewById(R.id.tab_layout2); //displaying tabs
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();
    }

    /**
     * Inisiasi AccountInvoiceFragment sebagai fragment pertama
     * dan StoreInvoiceFragment sebagai fragment kedua
     *
     * Selain itu, AccountInvoiceFragment juga dijadikan default
     * yang artinya dia merupakan fragment pertama yang dibuka ketika activity dijalankan
     */
    private class MyPagerAdapter extends FragmentStateAdapter {
        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }
        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return AccountInvoiceFragment.newInstance("fragment 1");
                }
                case 1: {
                    return StoreInvoiceFragment.newInstance("fragment 2");
                }
                default:
                    return AccountInvoiceFragment.newInstance("fragment 1, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    /**
     * Mengatur fungsi back button pada masing2 fragment
     */
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
// If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.d
            super.onBackPressed();
        } else {
// Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}