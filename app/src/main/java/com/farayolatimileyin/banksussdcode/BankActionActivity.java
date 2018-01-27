package com.farayolatimileyin.banksussdcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActionActivity extends AppCompatActivity {

    @BindView(R.id.bank_icon) ImageView bankImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_action);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("bankName");
        String bankIconName = intent.getStringExtra("bankImageName");
        getSupportActionBar().setTitle(name);
        bankImage.setImageDrawable(HomeActivity.getDrawable(this,bankIconName));
    }
}
