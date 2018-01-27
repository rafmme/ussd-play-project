package com.farayolatimileyin.banksussdcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
    ArrayList<BankUssdData> actionList = new ArrayList<>();
    String ussdCode;

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
        ussdCode = actionList.get(clickedItemIndex).getUssdCode();
        if (ussdCode.endsWith("#")){
            dial(ussdCode);
        }
        Toast.makeText(this,ussdCode,Toast.LENGTH_LONG).show();
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

}
