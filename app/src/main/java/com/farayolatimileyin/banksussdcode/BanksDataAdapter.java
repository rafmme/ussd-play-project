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
 * Created by FARAYOLA-FBTS on 26/01/2018.
 */

public class BanksDataAdapter extends RecyclerView.Adapter<BanksDataAdapter.BanksViewHolder> {

    private ArrayList<BanksData> banksList;
    private Context mContext;
    final private GridItemClickListener gridItemClickListener;

    public BanksDataAdapter(Context context, ArrayList<BanksData> listOfBanks, GridItemClickListener gridItemClickListener){
        this.banksList = listOfBanks;
        this.mContext = context;
        this.gridItemClickListener = gridItemClickListener;
    }

    @Override
    public BanksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banks_data_layout,parent,false);
        return new BanksViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(BanksViewHolder holder, int position) {
        BanksData bankData = banksList.get(position);
        holder.bankName.setText(bankData.getBankName());
        holder.bankImage.setImageDrawable(HomeActivity.getDrawable(mContext,bankData.getBankIcon()));
    }

    @Override
    public int getItemCount() {
        return banksList.size();
    }

    public interface GridItemClickListener{
        void onGridItemClickListener(int clickedItemIndex);
    }

    public class BanksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView bankName;
        ImageView bankImage;
        public BanksViewHolder(View view){
            super(view);
            bankName = (TextView) view.findViewById(R.id.bank_name);
            bankImage = (ImageView) view.findViewById(R.id.bank_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            gridItemClickListener.onGridItemClickListener(clickedPosition);
        }
    }
}
