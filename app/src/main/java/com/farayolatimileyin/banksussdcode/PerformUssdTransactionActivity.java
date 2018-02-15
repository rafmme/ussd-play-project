package com.farayolatimileyin.banksussdcode;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PerformUssdTransactionActivity extends AppCompatActivity {

    ImageView imgView;
    String imageName,ussdCode,receipientName,receipientAcctNum;
    Button cBtn,pBtn;
    EditText amount,receipient;
    static final int RESULT_CODE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getStringExtra("action");
        ussdCode = intent.getStringExtra("ussd");
        imageName = intent.getStringExtra("bImageName");
        int layoutRid = intent.getIntExtra("layout",R.layout.money_transfer_layout);
        setRightView(layoutRid);
        getSupportActionBar().setTitle(action);
    }

    public void setRightView(int layoutId){
        switch (layoutId){
            case R.layout.buy_airtime_self:
                buyAirtimeSelf(layoutId);
                break;
            case R.layout.buy_airtime_others_layout:
                buyAirtimeOthers(layoutId);
                break;
            case R.layout.money_transfer_layout:
                transferMoney(layoutId);
        }
    }

    public void buyAirtimeSelf(int layoutResId){
        setContentView(layoutResId);
        imgView = (ImageView) findViewById(R.id.bImage);
        imgView.setImageDrawable(HomeActivity.getDrawable(this,imageName));
    }

    public  void buyAirtimeOthers(int layoutResId){
        setContentView(layoutResId);
        imgView = (ImageView) findViewById(R.id.bImage);
        imgView.setImageDrawable(HomeActivity.getDrawable(this,imageName));
    }

    public void transferMoney(int layoutResId){
        setContentView(layoutResId);
        cBtn = (Button) findViewById(R.id.btn_load_account);
        pBtn = (Button) findViewById(R.id.transfer_button);
        receipient = (EditText) findViewById(R.id.account_num);
        amount = (EditText) findViewById(R.id.amount);

        cBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Load Contact "+ussdCode,Toast.LENGTH_LONG).show();
                Intent accountNumberPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(accountNumberPickerIntent,RESULT_CODE);
            }
        });

        pBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RESULT_CODE:
                    receipientPicked(data);
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

}
