package com.farayolatimileyin.banksussdcode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by FARAYOLA-FBTS on 27/01/2018.
 */

public class BankActionAdapter extends RecyclerView.Adapter<BankActionAdapter.BankActionViewHolder> {

    private Context context;
    private ArrayList<BankUssdData> listOfBankUssdActions;
    BankActionAdapter(Context context, ArrayList<BankUssdData>listOfBankUssdActions){
        this.context = context;
        this.listOfBankUssdActions = listOfBankUssdActions;
    }

    @Override
    public void onBindViewHolder(BankActionViewHolder holder, int position) {

    }

    @Override
    public BankActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate();
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BankActionViewHolder extends RecyclerView.ViewHolder{
        BankActionViewHolder(View view){
            super(view);
        }
    }
}
