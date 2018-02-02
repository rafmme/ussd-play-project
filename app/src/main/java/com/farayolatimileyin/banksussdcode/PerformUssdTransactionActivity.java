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
        int layoutRid = intent.getIntExtra("layout",R.layout.money_transfer_layout);
        setRigthView(layoutRid);
        getSupportActionBar().setTitle(action);
    }

    public void setRigthView(int layoutId){
        switch (layoutId){
            case R.layout.buy_airtime_self:
                buyAirtimeSelf(layoutId);
                break;
            case R.layout.buy_airtime_others_layout:
                buyAirtimeOthers(layoutId);
                break;
        }
    }

    public void buyAirtimeSelf(int layoutResId){
        setContentView(layoutResId);
    }

    public  void buyAirtimeOthers(int layoutResId){
        setContentView(layoutResId);
    }
}
