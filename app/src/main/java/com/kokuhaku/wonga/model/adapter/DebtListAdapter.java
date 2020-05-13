package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.DandR;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.viewmodel.DandRViewModel;

import java.util.ArrayList;
import java.util.List;

public class DebtListAdapter extends RecyclerView.Adapter<DebtListAdapter.DebtListHolder> {
    List<DandR> allDebts = new ArrayList<>();
    public onItemClickListener listener;

    public interface onItemClickListener{
        void onPayClick(int position);
    }

    @NonNull
    @Override
    public DebtListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.debts_item_list, parent, false);
        return new DebtListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtListHolder holder, int position) {

        DandR currDebt = allDebts.get(position);

        holder.keSiapa.setText(currDebt.getPerson());
        holder.tanggal.setText(AppUtils.getFormattedDateString(currDebt.getTanggal()));
        holder.jumlah.setText(String.valueOf(currDebt.getJumlah()));
        holder.payBtn.setText("Pay");
        holder.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onPayClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allDebts.size();
    }

    public void SetDebtList(List<DandR> dandRS){
        allDebts = dandRS;
        notifyDataSetChanged();
    }

    public DandR GetDandR(int position){
        return allDebts.get(position);
    }

    class DebtListHolder extends RecyclerView.ViewHolder{
        private TextView keSiapa;
        private TextView tanggal;
        private TextView jumlah;
        private Button payBtn;

        public DebtListHolder(@NonNull View itemView) {
            super(itemView);

            keSiapa = itemView.findViewById(R.id.ke_siapa_dandr);
            tanggal = itemView.findViewById(R.id.tanggal_dandr);
            jumlah = itemView.findViewById(R.id.jumlah_dandr);
            payBtn = itemView.findViewById(R.id.pay_btn_dandr);
        }
    }

    public void setOnClickListener(onItemClickListener _listener){
        listener = _listener;
    }
}
