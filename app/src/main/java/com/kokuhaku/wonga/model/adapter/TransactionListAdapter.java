package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.Balance;
import com.kokuhaku.wonga.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListHolder> {

    private List<Balance> allBalances = new ArrayList<>();

    @NonNull
    @Override
    public TransactionListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_transaction_item, parent, false);
        return new TransactionListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListHolder holder, int position) {
        Balance currentBalance = allBalances.get(position);

        holder.source.setText(currentBalance.getSumber());
        holder.date.setText(AppUtils.getFormattedDateString(currentBalance.getTanggal()));
        holder.jumlah.setText(String.valueOf(currentBalance.getJumlah()));
    }

    @Override
    public int getItemCount() {
        return allBalances.size();
    }

    public void SetBalances(List<Balance> balances){
        allBalances = balances;
        notifyDataSetChanged();
    }

    class TransactionListHolder extends RecyclerView.ViewHolder{
        private TextView source;
        private TextView date;
        private TextView jumlah;

        public TransactionListHolder(@NonNull View itemView) {
            super(itemView);

            source = itemView.findViewById(R.id.source_balance_item);
            date = itemView.findViewById(R.id.date_balance_item);
            jumlah = itemView.findViewById(R.id.jumlah_balance_item);
        }
    }
}
