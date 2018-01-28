package com.farayolatimileyin.banksussdcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

public class HomeActivity extends AppCompatActivity implements BanksDataAdapter.GridItemClickListener {

    RecyclerView rv_banks;
    RecyclerView.Adapter mAdapter;
    ArrayList<BanksData> banksDataList = new ArrayList<>();
    String[] bankNames;
    String[] bankImageNames;
    BankUssdData bankUssdData;
    ArrayList<BankUssdData> bankUssdDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateListOfBanks();
        rv_banks = (RecyclerView) findViewById(R.id.rv_banks);
        rv_banks.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        rv_banks.setItemAnimator(new DefaultItemAnimator());
        rv_banks.setHasFixedSize(true);
        mAdapter = new BanksDataAdapter(this, banksDataList, this);
        rv_banks.setAdapter(mAdapter);

    }

    public void populateListOfBanks(){
        bankNames = getResources().getStringArray(R.array.bankNamesArray);
        bankImageNames = getResources().getStringArray(R.array.bankImageNamesArray);
        for(int i = 0; i<bankNames.length; i++){
            BanksData banksData = new BanksData(bankNames[i],bankImageNames[i]);
            banksDataList.add(banksData);
        }
    }

    public static Drawable getDrawable(Context context, String imageName){
        int resourceId = context.getResources().getIdentifier(imageName,"drawable",context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    @Override
    public void onGridItemClickListener(int clickedItemIndex) {
        Intent intent = new Intent(HomeActivity.this,BankActionActivity.class);
        setBank(banksDataList.get(clickedItemIndex).getBankIcon());
        intent.putExtra("bankName",banksDataList.get(clickedItemIndex).getBankName());
        intent.putExtra("bankImageName",banksDataList.get(clickedItemIndex).getBankIcon());
        intent.putParcelableArrayListExtra("bankussdlist",bankUssdDataList);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }

    public void setBank(String bank){
        switch (bank){
            case "accessbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfAccessBankAction), getResources().getStringArray(R.array.arrayOfAccessBankUssd));
                break;
            case "diamondbank":
                bankUssdDataList.clear();
                break;
            case "ecobank":
                bankUssdDataList.clear();
                break;
            case "fcmbbank":
                bankUssdDataList.clear();
                break;
            case "fidelitybank":
                bankUssdDataList.clear();
                break;
            case "firstbank":
                bankUssdDataList.clear();
                break;
            case "gtbank":
                bankUssdDataList.clear();
                break;
            case "heritagebank":
                bankUssdDataList.clear();
                break;
            case "keystonebank":
                bankUssdDataList.clear();
                break;
            case "skyebank":
                bankUssdDataList.clear();
                break;
            case "stanbicibtcbank":
                bankUssdDataList.clear();
                break;
            case "sterlingbank":
                bankUssdDataList.clear();
                break;
            case "ubabank":
                bankUssdDataList.clear();
                break;
            case "unionbank":
                bankUssdDataList.clear();
                break;
            case "unitybank":
                bankUssdDataList.clear();
                break;
            case "wemabank":
                bankUssdDataList.clear();
                break;
            case "zenithbank":
                bankUssdDataList.clear();
                break;
        }
    }

    public void populateBankUssdList(String bank, String[] arrayOfActions, String[] arrayOfUssdCodes){
        bankUssdDataList = new ArrayList<>();
        bankUssdDataList.clear();
        for (int i = 0; i < arrayOfActions.length; i++){
            bankUssdDataList.add(new BankUssdData(bank,arrayOfActions[i],arrayOfUssdCodes[i]));
        }
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
