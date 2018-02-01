package com.farayolatimileyin.banksussdcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PerformUssdTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
        setContentView(intent.getIntExtra("layout",R.layout.money_transfer_layout));
        getSupportActionBar().setTitle(action);
    }
}
