package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.Balance;

import java.util.ArrayList;
import java.util.List;

public class CurrentBalanceAdapter extends RecyclerView.Adapter<CurrentBalanceAdapter.CurrentBalanceHolder> {
    List<Balance> allBalances = new ArrayList<>();

    @NonNull
    @Override
    public CurrentBalanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.curr_balance, parent, false);
        return new CurrentBalanceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentBalanceHolder holder, int position) {
        Balance currBalance = allBalances.get(position);

        holder.currentBalance.setText(String.valueOf(currBalance.getTotalUang()));
    }

    @Override
    public int getItemCount() {
        return allBalances.size();
    }

    public void SetBalances(List<Balance> balances){
        allBalances = balances;
        notifyDataSetChanged();
    }

    class CurrentBalanceHolder extends RecyclerView.ViewHolder{
        private TextView currentBalance;

        public CurrentBalanceHolder(@NonNull View itemView) {
            super(itemView);
            currentBalance = itemView.findViewById(R.id.current_balance_balance);
        }
    }
}
