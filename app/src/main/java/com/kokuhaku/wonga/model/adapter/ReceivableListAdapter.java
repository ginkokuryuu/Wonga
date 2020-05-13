package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.DandR;
import com.kokuhaku.wonga.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ReceivableListAdapter extends RecyclerView.Adapter<ReceivableListAdapter.ReceivableListHolder> {
    List<DandR> allReceivables = new ArrayList<>();
    public onItemClickListener listener;

    public interface onItemClickListener{
        void onPaidClick(int position);
    }

    @NonNull
    @Override
    public ReceivableListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.debts_item_list, parent, false);
        return new ReceivableListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivableListHolder holder, int position) {
        DandR currReceiv = allReceivables.get(position);

        holder.keSiapa.setText(currReceiv.getPerson());
        holder.tanggal.setText(AppUtils.getFormattedDateString(currReceiv.getTanggal()));
        holder.jumlah.setText(String.valueOf(currReceiv.getJumlah()));
        holder.payBtn.setText("Paid");
        holder.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onPaidClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allReceivables.size();
    }

    public DandR GetDandR(int position){
        return allReceivables.get(position);
    }

    public void SetReceivableList(List<DandR> dandRS){
        allReceivables = dandRS;
        notifyDataSetChanged();
    }

    class ReceivableListHolder extends RecyclerView.ViewHolder{
        private TextView keSiapa;
        private TextView tanggal;
        private TextView jumlah;
        private Button payBtn;

        public ReceivableListHolder(@NonNull View itemView) {
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
