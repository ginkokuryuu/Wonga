package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.Expenses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MedicalListAdapter extends RecyclerView.Adapter<MedicalListAdapter.MedicalListHolder> {
    private List<Expenses> allMedics = new ArrayList<>();
    private static DateFormat tanggalFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat jamFormat = new SimpleDateFormat("HH:mm:ss");

    @NonNull
    @Override
    public MedicalListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_item_list, parent, false);
        return new MedicalListHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalListHolder holder, int position) {
        Expenses currMedic = allMedics.get(position);
        TimeZone timeZone = TimeZone.getTimeZone("Etc/GMT+7");
        jamFormat.setTimeZone(timeZone);
        tanggalFormat.setTimeZone(timeZone);

        String tanggal = tanggalFormat.format(currMedic.getTanggal());
        String jam = jamFormat.format(currMedic.getTanggal());

        holder.tanggal.setText(tanggal);
        holder.jumlah.setText(String.valueOf(currMedic.getJumlah()));
        holder.jam.setText(jam);
    }

    @Override
    public int getItemCount() {
        return allMedics.size();
    }

    public void SetMedics(List<Expenses> medics){
        allMedics = medics;
        notifyDataSetChanged();
    }

    class MedicalListHolder extends RecyclerView.ViewHolder{
        private TextView tanggal;
        private TextView jumlah;
        private TextView jam;

        public MedicalListHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tanggal_expenses_list);
            jumlah = itemView.findViewById(R.id.jumlah_expenses_list);
            jam = itemView.findViewById(R.id.jam_expenses_list);
        }
    }
}
