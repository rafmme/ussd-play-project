package com.farayolatimileyin.banksussdcode;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.rv_banks) RecyclerView rv_banks;
    RecyclerView.Adapter mAdapter;
    public static Activity activity;
    ArrayList<BanksData> banksDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateListOfBanks();
        activity = this;
        ButterKnife.bind(this);
        rv_banks.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        rv_banks.setItemAnimator(new DefaultItemAnimator());
        rv_banks.setHasFixedSize(true);
        mAdapter = new BanksDataAdapter(banksDataList);
        rv_banks.setAdapter(mAdapter);

    }

    public void populateListOfBanks(){
        banksDataList.add(new BanksData("hhh","der","shf"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),"settings clicked",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_app_info:
                Toast.makeText(getApplicationContext(),"App info clicked",Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
