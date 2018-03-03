package com.farayolatimileyin.banksussdcode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

    String ussdCode;
    RecyclerView rv_banks;
    RecyclerView.Adapter mAdapter;
    ArrayList<BanksData> banksDataList = new ArrayList<>();
    String[] bankNames;
    String[] bankImageNames;
    BankUssdData bankUssdData;
    ArrayList<BankUssdData> bankUssdDataList;

    public static Drawable getDrawable(Context context, String imageName) {
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

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

    @Override
    public void onGridItemClickListener(int clickedItemIndex) {
        String bankName = banksDataList.get(clickedItemIndex).getBankName();
        String bankImageName = banksDataList.get(clickedItemIndex).getBankIcon();
        checkForBanksWithJustOneAction(bankName, bankImageName);
    }

    public void checkForBanksWithJustOneAction(String bankName, String bankImageName) {
        if (bankName.equalsIgnoreCase(getResources().getString(R.string.eco)) || bankName.equalsIgnoreCase(getResources().getString(R.string.fcmb))
                || bankName.equalsIgnoreCase(getResources().getString(R.string.heritage)) || bankName.equalsIgnoreCase(getResources().getString(R.string.keystone))
                || bankName.equalsIgnoreCase(getResources().getString(R.string.skye)) || bankName.equalsIgnoreCase(getResources().getString(R.string.stanbic))
                || bankName.equalsIgnoreCase(getResources().getString(R.string.union)) || bankName.equalsIgnoreCase(getResources().getString(R.string.unity))) {

            switch (bankName) {

                case "Eco Bank":
                    ussdCode = getResources().getString(R.string.eco_ussd1);
                    dial(ussdCode);
                    break;
                case "FCMB":
                    ussdCode = getResources().getString(R.string.fcmb_ussd1);
                    dial(ussdCode);
                    break;
                case "Heritage Bank":
                    ussdCode = getResources().getString(R.string.h_ussd1);
                    dial(ussdCode);
                    break;
                case "Keystone Bank":
                    ussdCode = getResources().getString(R.string.k_ussd1);
                    dial(ussdCode);
                    break;
                case "Skye Bank":
                    ussdCode = getResources().getString(R.string.skye_ussd);
                    dial(ussdCode);
                    break;
                case "Stanbic IBTC Bank":
                    ussdCode = getResources().getString(R.string.stb_ussd1);
                    dial(ussdCode);
                    break;
                case "Union Bank":
                    ussdCode = getResources().getString(R.string.union_ussd1);
                    dial(ussdCode);
                    break;
                case "Unity Bank":
                    ussdCode = getResources().getString(R.string.unity_ussd1);
                    dial(ussdCode);
                    break;

            }
        } else {
            Intent intent = new Intent(HomeActivity.this, BankActionActivity.class);
            setBank(bankImageName);
            intent.putExtra("bankName", bankName);
            intent.putExtra("bankImageName", bankImageName);
            intent.putParcelableArrayListExtra("bankussdlist", bankUssdDataList);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        }
    }

    public void setBank(String bank){
        switch (bank){
            case "accessbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfAccessBankAction), getResources().getStringArray(R.array.arrayOfAccessBankUssd));
                break;
            case "diamondbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfDiamondBankAction), getResources().getStringArray(R.array.arrayOfDiamondBankUssd));
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
            case "quickteller":
                populateBankUssdList(bank,getResources().getStringArray(R.array.arrayOfQuicktellerBankAction),getResources().getStringArray(R.array.arrayOfQuicktellerBankUssd));
                break;
            case "sterlingbank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfSterlingBankAction),getResources().getStringArray(R.array.arrayOfSterlingBankUssd));
                break;
            case "ubabank":
                populateBankUssdList(bank, getResources().getStringArray(R.array.arrayOfUbaBankAction), getResources().getStringArray(R.array.arrayOfUbaBankUssd));
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

    public void dial(String uCode) {
        String code = Uri.encode(uCode);
        if (Build.VERSION.SDK_INT < 23) {
            makeCall(code);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                makeCall(code);
            } else {
                final String[] PERMISSION_STORAGE = {Manifest.permission.CALL_PHONE};
                ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, 9);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionGranted = false;
        String code = Uri.encode(ussdCode);
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            makeCall(code);
        } else {
            Toast.makeText(this, "Call Permission not granted", Toast.LENGTH_LONG).show();
        }
    }

    public void makeCall(String code) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "button clicked " + code, Toast.LENGTH_LONG).show();
            Intent dialIntent = new Intent(Intent.ACTION_CALL);
            dialIntent.setData(Uri.parse("tel:" + code));
            this.startActivity(dialIntent);
        } else {
            Toast.makeText(this, "Call Permission not granted", Toast.LENGTH_LONG).show();
        }

    }

}
