package com.farayolatimileyin.banksussdcode;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FARAYOLA-FBTS on 26/01/2018.
 */

public class BanksDataAdapter extends RecyclerView.Adapter<BanksDataAdapter.BanksViewHolder> {

    private ArrayList<BanksData> banksList;

    public BanksDataAdapter(ArrayList<BanksData> listOfBanks){
        this.banksList = listOfBanks;
    }

    @Override
    public BanksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banks_data_layout,parent,false);
        return new BanksViewHolder(itemView);
    }

    public Drawable getDrawable(String imageName){
        Context context = HomeActivity.activity;
        int resourceId = context.getResources().getIdentifier(imageName,"drawable",context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    @Override
    public void onBindViewHolder(BanksViewHolder holder, int position) {
        BanksData bankData = banksList.get(position);
        holder.bankName.setText(bankData.getBankName());
        holder.bankImage.setImageDrawable(getDrawable(bankData.getBankIcon()));
        holder.bankUssd.setText(bankData.getBankUssdCode());
    }

    @Override
    public int getItemCount() {
        return banksList.size();
    }

    public class BanksViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bank_name) TextView bankName;
        @BindView(R.id.bank_image) ImageView bankImage;
        @BindView(R.id.bank_ussd) TextView bankUssd;
        public BanksViewHolder(View view){
            super(view);
            ButterKnife.bind(HomeActivity.activity);
        }

    }
}
