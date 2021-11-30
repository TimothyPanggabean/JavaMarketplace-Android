package com.TimothyJmartKD.jmart_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.TimothyJmartKD.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mainText = findViewById(R.id.mainText);
        mainText.setText("Hello, " + LoginActivity.getLoggedAccount().name + "!");
        mainText.setTextSize(20);
    }
}