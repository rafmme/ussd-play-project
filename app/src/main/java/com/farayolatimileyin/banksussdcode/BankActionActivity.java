package com.farayolatimileyin.banksussdcode;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActionActivity extends AppCompatActivity implements BankActionAdapter.BankActionGridItemClickListener {

    static final int RESULT_CODE = 7;
    static final int N_RESULT_CODE = 8;
    @BindView(R.id.bank_icon) ImageView bankImage;
    RecyclerView rv_action;
    RecyclerView.Adapter actionAdapter;
    ArrayList<BankUssdData> actionList = new ArrayList<>();
    String ussdCode, bankName, amount;
    EditText receipient;
    View view;


    public static String cleanAmountString(String amountWithNairaSign){
        String b = "";
        if (amountWithNairaSign.contains("₦")){
            String[] a = amountWithNairaSign.split(" ");

            for(String i : a[1].split(",")){
                b += i;
            }
            return b;
        }
        else {
            for(String i : amountWithNairaSign.split(",")){
                b += i;
            }
            return b;
        }

    }

    public static String removeNameFromContact(String contactWithName) {
        if (contactWithName.contains(":")) {
            String[] h = contactWithName.split("[:]");
            return h[1];
        } else {
            return contactWithName;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_action);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        bankName = intent.getStringExtra("bankName");
        String bankIconName = intent.getStringExtra("bankImageName");
        actionList = intent.getParcelableArrayListExtra("bankussdlist");
        getSupportActionBar().setTitle(bankName);
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
            showPerformUssdDialog(actionList.get(clickedItemIndex).getActionName(),actionList.get(clickedItemIndex).getUssdCode(),bankName);
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

    public void performUssdTransaction(String action, String ussd, String amount, String phoneNumber, String acctNumber){
        String ud = ussd+cleanAmountString(amount)+"*"+removeNameFromContact(phoneNumber)+"#";
        confirmAction(action,ud);
    }

    public void confirmAction(String action, final String completeUssdCode){
        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(BankActionActivity.this);
        confirmDialog.setCancelable(false);
        confirmDialog.setTitle("Confirm Action");
        confirmDialog.setMessage(action);
        confirmDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dial(completeUssdCode);
            }
        });
        confirmDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        AlertDialog transactionConfirmationDialog = confirmDialog.create();
        transactionConfirmationDialog.show();
    }

    public View makeDialogView(int layoutResource){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(layoutResource, null);
        return view;
    }

    public void createDialog(View view){
        AlertDialog.Builder ussdActionDialog = new AlertDialog.Builder(BankActionActivity.this);
        ussdActionDialog.setView(view);
        ussdActionDialog.setCancelable(true);
        ussdActionDialog.setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                amount = null;
                return;
            }
        });
        AlertDialog ussdDialog = ussdActionDialog.create();
        ussdDialog.show();
    }

    public void showPerformUssdDialog(final String action_name, final String ussd, /*for headertext in transfer money layout*/String bName){
        if(action_name.startsWith("Transfer money")){
            view = makeDialogView(R.layout.money_transfer_layout);
            receipient = (EditText) view.findViewById(R.id.accountNumber);
            final EditText amountText = (EditText) view.findViewById(R.id.amount);
            amountText.addTextChangedListener(new NumberTextWatcherForThousand(amountText));
            Button pickAcctNumFromContactListBtn = (Button) view.findViewById(R.id.pickAcctNumBtn);
            pickAcctNumFromContactListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent accountNumberPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(accountNumberPickerIntent,RESULT_CODE);
                }
            });
            Button transferBtn = (Button) view.findViewById(R.id.transferBtn);
            transferBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performUssdTransaction(action_name, ussd, amountText.getText().toString(), receipient.getText().toString(), null);
                }
            });
            createDialog(view);
            return;
        }

        switch (action_name){
            case "Buy airtime for self":
                view = makeDialogView(R.layout.buy_airtime_self);
                populateSpinnerWithAirtimeAmount(view);
                createDialog(view);
                break;
            case "Buy airtime":
            case "Buy airtime for others":
                view = makeDialogView(R.layout.buy_airtime_others_layout);
                receipient = (EditText) view.findViewById(R.id.phoneNumber);
                populateSpinnerWithAirtimeAmount(view);
                Button pickPhoneNumFromContactListBtn = (Button) view.findViewById(R.id.pickNumberBtn);
                pickPhoneNumFromContactListBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent phoneNumberPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        startActivityForResult(phoneNumberPickerIntent,N_RESULT_CODE);
                    }
                });
                Button buyBtn = (Button) view.findViewById(R.id.buyBtn);
                buyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performUssdTransaction(action_name,ussd,amount,receipient.getText().toString(),null);
                    }
                });
                createDialog(view);
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
                receipient.setText(cursor.getString(receipientNameIndex) + ":" + cursor.getString(receipientAcctNumIndex));

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
                receipient.setText(cursor.getString(receipientNameIndex) + ":" + formatPhoneNumber(cursor.getString(receipientPhoneNumIndex)));

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void populateSpinnerWithAirtimeAmount(View view){
        String[] airtimeAmountArray = getResources().getStringArray(R.array.airtimeAmountArray);
        final Spinner amountSpinner = (Spinner) view.findViewById(R.id.amountSpinner);
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
                    amount = null;
                }
                else{
                    Toast.makeText(getApplicationContext(),amountSpinner.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                    amount = amountSpinner.getSelectedItem().toString();
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
