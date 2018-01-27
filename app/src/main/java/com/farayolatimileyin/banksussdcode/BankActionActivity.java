package com.farayolatimileyin.banksussdcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BankActionActivity extends AppCompatActivity {

    @BindView()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_action);
        Intent intent = getIntent();
        String name = intent.getStringExtra("bankName");
        String bankIconName = intent.getStringExtra("bankImageName");
        getSupportActionBar().setTitle(name);
    }
}
