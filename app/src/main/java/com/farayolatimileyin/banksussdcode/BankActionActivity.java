package com.farayolatimileyin.banksussdcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActionActivity extends AppCompatActivity implements BankActionAdapter.BankActionGridItemClickListener {

    @BindView(R.id.bank_icon) ImageView bankImage;
    RecyclerView rv_action;
    RecyclerView.Adapter actionAdapter;
    ArrayList<BankUssdData> actionList = new ArrayList<>();
    String ussdCode,receipientName,receipientAcctNum,receipientPhoneNumber;
    static final int RESULT_CODE = 7;
    static final int N_RESULT_CODE = 8;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        ussdCode = actionList.get(clickedItemIndex).getUssdCode();
        if (ussdCode.endsWith("#")){
            dial(ussdCode);
        }

        else{
            launchPerformUssdActivityIntent(actionList.get(clickedItemIndex).getActionName(),actionList.get(clickedItemIndex).getUssdCode(),actionList.get(clickedItemIndex).getBankName());
        }
    }

    public void dial(String uCode){
        String code = Uri.encode(uCode);
        if(Build.VERSION.SDK_INT < 23){
            makeCall(code);
        }
        else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                makeCall(code);
            }
            else{
                final String[] PERMISSION_STORAGE = {Manifest.permission.CALL_PHONE};
                ActivityCompat.requestPermissions(this,PERMISSION_STORAGE,9);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permissionGranted = false;
        String code = Uri.encode(ussdCode);
        switch (requestCode){
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted){
            makeCall(code);
        }
        else{
            Toast.makeText(this,"Call Permission not granted",Toast.LENGTH_LONG).show();
        }
    }

    public void makeCall(String code){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"button clicked "+code,Toast.LENGTH_LONG).show();
            Intent dialIntent = new Intent(Intent.ACTION_CALL);
            dialIntent.setData(Uri.parse("tel:"+code));
            this.startActivity(dialIntent);
        }
        else{
            Toast.makeText(this,"Call Permission not granted",Toast.LENGTH_LONG).show();
        }

    }

    public View makeDialog(int layoutResource){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(layoutResource, null);
        return view;
    }

    public void launchPerformUssdActivityIntent(String action_name, String ussd, String bImageName){
        Intent intent;
        if(action_name.startsWith("Transfer money")){
            intent = new Intent(BankActionActivity.this,PerformUssdTransactionActivity.class);
            intent.putExtra("action",action_name);
            intent.putExtra("ussd",ussd);
            intent.putExtra("bImageName",bImageName);
            intent.putExtra("layout",R.layout.money_transfer_layout);
            startActivity(intent);
            return;
        }

        switch (action_name){
            case "Buy airtime for self":
                intent = new Intent(BankActionActivity.this,PerformUssdTransactionActivity.class);
                intent.putExtra("action",action_name);
                intent.putExtra("ussd",ussd);
                intent.putExtra("bImageName",bImageName);
                intent.putExtra("layout",R.layout.buy_airtime_self);
                startActivity(intent);
                break;
            case "Buy airtime for others":
                intent = new Intent(BankActionActivity.this,PerformUssdTransactionActivity.class);
                intent.putExtra("action",action_name);
                intent.putExtra("ussd",ussd);
                intent.putExtra("bImageName",bImageName);
                intent.putExtra("layout",R.layout.buy_airtime_others_layout);
                startActivity(intent);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RESULT_CODE:
                    receipientPicked(data);
                    break;
                case N_RESULT_CODE:
                    receipientPhoneNumPicked(data);
                    break;
            }
        }
    }

    public void receipientPicked(Intent data){
        try{
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            if (cursor.moveToFirst()) {
                int receipientNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int receipientAcctNumIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                receipientAcctNum = cursor.getString(receipientAcctNumIndex);
                receipientName = cursor.getString(receipientNameIndex);
                receipient.setText(receipientName+" : "+receipientAcctNum);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void receipientPhoneNumPicked(Intent data){
        try{
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            if (cursor.moveToFirst()) {
                int receipientNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int receipientPhoneNumIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                receipientPhoneNumber = formatPhoneNumber(cursor.getString(receipientPhoneNumIndex));
                receipientName = cursor.getString(receipientNameIndex);
                receipient.setText(receipientName+" : "+receipientPhoneNumber);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateSpinnerWithAirtimeAmount(){
        String[] airtimeAmountArray = getResources().getStringArray(R.array.airtimeAmountArray);
        amountSpinner = (Spinner) findViewById(R.id.air_amount);
        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(this,android.R.layout.simple_list_item_1);
        mySpinnerAdapter.addAll(airtimeAmountArray);
        mySpinnerAdapter.add(getResources().getString(R.string.holderText));
        amountSpinner.setAdapter(mySpinnerAdapter);
        amountSpinner.setSelection(mySpinnerAdapter.getCount());
        amountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(amountSpinner.getSelectedItem().toString() == "Airtime Amount (₦)"){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.holderText),Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),amountSpinner.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public String formatPhoneNumber(String rawNumber){
        String cPN = "";
        if(rawNumber.startsWith("+234")) {
            cPN += "0";
            String[] numArray = rawNumber.substring(4,rawNumber.length()).split("");
            for(String i : numArray){
                if((!i.equals("-"))&& (!i.equals(" "))){
                    cPN += i;
                }
            }

        }

        else{
            if (rawNumber.startsWith("0")) {
                String[] numArray = rawNumber.split("");
                for (String i : numArray) {
                    if ((!i.equals("-")) && (!i.equals(" "))) {
                        cPN += i;
                    }
                }
            }

            else{
                cPN = rawNumber;
            }
        }
        return cPN;
    }


}
