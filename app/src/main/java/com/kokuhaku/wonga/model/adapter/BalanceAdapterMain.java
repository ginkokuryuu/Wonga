package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;

public class BalanceAdapterMain extends RecyclerView.Adapter<BalanceAdapterMain.BalanceHolderMain> {
    private int currentBalance = 0;

    @NonNull
    @Override
    public BalanceHolderMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.balance_item_main, parent, false);
        return new BalanceHolderMain(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceHolderMain holder, int position) {
        holder.currentBalanceTV.setText(String.valueOf(currentBalance));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void SetCurrentBalance(Integer currentBalance){
        this.currentBalance = currentBalance;
        notifyDataSetChanged();
    }

    class BalanceHolderMain extends RecyclerView.ViewHolder{
        private TextView currentBalanceTV;

        public BalanceHolderMain(@NonNull View itemView) {
            super(itemView);
            currentBalanceTV = itemView.findViewById(R.id.current_balance_main);
        }
    }
}
