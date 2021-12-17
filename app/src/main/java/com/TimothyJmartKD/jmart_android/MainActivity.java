package com.TimothyJmartKD.jmart_android;

import static com.TimothyJmartKD.jmart_android.ProductsFragment.productReturned;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.TimothyJmartKD.R;
import com.TimothyJmartKD.jmart_android.model.Product;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    //The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    public static ViewPager2 viewPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private FragmentStateAdapter pagerAdapter;
    // Arrey of strings FOR TABS TITLES
    private String[] titles = new String[]{"Products", "Filter"};
    // tab titles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.mypager2);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter); //inflating tab layout
        TabLayout tabLayout =( TabLayout) findViewById(R.id.tab_layout2); //displaying tabs
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();
    }

    private class MyPagerAdapter extends FragmentStateAdapter {
        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }
        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return ProductsFragment.newInstance("fragment 1");
                }
                case 1: {
                    return FilterFragment.newInstance("fragment 2");
                }
                default:
                    return ProductsFragment.newInstance("fragment 1, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_box:
                Intent toCreateProductPage = new Intent(MainActivity.this, CreateProductActivity.class);
                startActivity(toCreateProductPage);
                return true;
            case R.id.person:
                Intent toAboutMePage = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(toAboutMePage);
                return true;
            case R.id.sticky_note_2:
                Intent toInvoicePage = new Intent(MainActivity.this, InvoiceActivity.class);
                startActivity(toInvoicePage);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.add_box).setVisible(LoginActivity.getLoggedAccount().store != null);
        androidx.appcompat.widget.SearchView search = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.search).getActionView();
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1, productReturned);
        ListView listView = findViewById(R.id.productsList);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1, productReturned);
                listView.setAdapter(adapter);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}