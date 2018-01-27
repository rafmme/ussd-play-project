package com.farayolatimileyin.banksussdcode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by FARAYOLA-FBTS on 27/01/2018.
 */

public class BankActionAdapter extends RecyclerView.Adapter<BankActionAdapter.BankActionViewHolder> {

    private Context context;
    private ArrayList<BankUssdData> listOfBankUssdActions;
    private final BankActionGridItemClickListener bankActionGridItemClickListener;

    public BankActionAdapter(Context context, ArrayList<BankUssdData>listOfBankUssdActions, BankActionGridItemClickListener bankActionGridItemClickListener){
        this.context = context;
        this.listOfBankUssdActions = listOfBankUssdActions;
        this.bankActionGridItemClickListener = bankActionGridItemClickListener;
    }

    @Override
    public void onBindViewHolder(BankActionViewHolder holder, int position) {
        BankUssdData bankUssdData = listOfBankUssdActions.get(position);
        holder.icon.setImageDrawable(HomeActivity.getDrawable(context,bankUssdData.getBankName()));
        holder.action.setText(bankUssdData.getActionName());
        holder.actionUssdCode.setText(bankUssdData.getUssdCode());
    }

    @Override
    public BankActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_ussd_action_layout,parent,false);
        return new BankActionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return listOfBankUssdActions.size();
    }

    public interface BankActionGridItemClickListener{
        void onBankActionGridItemClickListener(int clickedItemIndex);
    }

    class BankActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView action;
        TextView actionUssdCode;
        BankActionViewHolder(View view){
            super(view);
            icon = (ImageView) view.findViewById(R.id.b_icon);
            action = (TextView) view.findViewById(R.id.action_name);
            actionUssdCode = (TextView) view.findViewById(R.id.action_ussd);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            bankActionGridItemClickListener.onBankActionGridItemClickListener(clickedPosition);
        }
    }
}
