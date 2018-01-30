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
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfDiamondBankAction), getResources().getStringArray(R.array.arrayOfDiamondBankUssd));
                break;
            case "ecobank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfEcoBankAction),getResources().getStringArray(R.array.arrayOfEcoBankUssd));
                break;
            case "fcmbbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfFcmbBankAction),getResources().getStringArray(R.array.arrayOfFcmbBankUssd));
                break;
            case "fidelitybank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfFidelityBankAction),getResources().getStringArray(R.array.arrayOfFidelityBankUssd));
                break;
            case "firstbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfFirstBankAction),getResources().getStringArray(R.array.arrayOfFirstBankUssd));
                break;
            case "gtbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfGTBankAction),getResources().getStringArray(R.array.arrayOfGTBankUssd));
                break;
            case "heritagebank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfHeritageBankAction),getResources().getStringArray(R.array.arrayOfHeritageBankUssd));
                break;
            case "keystonebank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfKeystoneBankAction),getResources().getStringArray(R.array.arrayOfKeystoneBankUssd));
                break;
            case "quickteller":
                populateBankUssdList(bank,getResources().getStringArray(R.array.arrayOfQuicktellerBankAction),getResources().getStringArray(R.array.arrayOfQuicktellerBankUssd));
                break;
            case "skyebank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfSkyeBankAction),getResources().getStringArray(R.array.arrayOfSkyeBankUssd));
                break;
            case "stanbicibtcbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfStanbicIbtcBankAction),getResources().getStringArray(R.array.arrayOfStanbicIbtcBankUssd));
                break;
            case "sterlingbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfSterlingBankAction),getResources().getStringArray(R.array.arrayOfSterlingBankUssd));
                break;
            case "ubabank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfUbaBankAction), getResources().getStringArray(R.array.arrayOfUbaBankUssd));
                break;
            case "unionbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfUnionBankAction),getResources().getStringArray(R.array.arrayOfUnionBankUssd));
                break;
            case "unitybank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfUnityBankAction),getResources().getStringArray(R.array.arrayOfUnityBankUssd));
                break;
            case "wemabank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfWemaBankAction), getResources().getStringArray(R.array.arrayOfWemaBankUssd));
                break;
            case "zenithbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfZenithBankAction), getResources().getStringArray(R.array.arrayOfZenithBankUssd));
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
