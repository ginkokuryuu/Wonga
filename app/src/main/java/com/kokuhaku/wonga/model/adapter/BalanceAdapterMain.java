package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.Balance;

public class BalanceAdapterMain extends RecyclerView.Adapter<BalanceAdapterMain.BalanceHolderMain> {
    private Integer currentBalance = new Integer("0");
    public onItemClickListener listener;

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

    public void SetCurrentBalance(Integer _currentBalance){
        if(currentBalance != null) {
            currentBalance = _currentBalance;
        }
        else{
            currentBalance = 0;
        }
        notifyDataSetChanged();
    }

    public int GetCurrentBalance(){
        return currentBalance;
    }

    class BalanceHolderMain extends RecyclerView.ViewHolder{
        private TextView currentBalanceTV;

        public BalanceHolderMain(@NonNull View itemView) {
            super(itemView);
            currentBalanceTV = itemView.findViewById(R.id.current_balance_main);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(currentBalance);
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(int currBalance);
    }

    public void setOnClickListener(onItemClickListener _listener){
        listener = _listener;
    }
}
