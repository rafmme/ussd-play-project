package com.farayolatimileyin.banksussdcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActionActivity extends AppCompatActivity implements BankActionAdapter.BankActionGridItemClickListener {

    @BindView(R.id.bank_icon) ImageView bankImage;
    RecyclerView rv_action;
    RecyclerView.Adapter actionAdapter;
    ArrayList<BankUssdData> actionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_action);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("bankName");
        String bankIconName = intent.getStringExtra("bankImageName");
        actionList = intent.getParcelableArrayListExtra("bankussdlist");
        getSupportActionBar().setTitle(name);
        bankImage.setImageDrawable(HomeActivity.getDrawable(this,bankIconName));
        loadRecylerView();
    }

    public void loadRecylerView(){
        rv_action = (RecyclerView) findViewById(R.id.rv_bank_action);
        rv_action.setLayoutManager(new LinearLayoutManager(this));
        actionAdapter = new BankActionAdapter(this,actionList,this);
        rv_action.setAdapter(actionAdapter);
    }

    @Override
    public void onBankActionGridItemClickListener(int clickedItemIndex) {
        Toast.makeText(this,actionList.get(clickedItemIndex).getUssdCode(),Toast.LENGTH_LONG).show();
    }
}
