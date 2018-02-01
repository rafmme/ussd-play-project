package com.farayolatimileyin.banksussdcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PerformUssdTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_transfer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
