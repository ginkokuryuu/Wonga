package com.kokuhaku.wonga.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.Report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportListHolder> {
    private List<Report> allReport = new ArrayList<>();
    private static DateFormat bulanFormat = new SimpleDateFormat("MMMM");
    private static DateFormat tahunFormat = new SimpleDateFormat("yyyy");

    @NonNull
    @Override
    public ReportListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        return new ReportListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportListHolder holder, int position) {
        TimeZone timeZone = TimeZone.getTimeZone("Etc/GMT+7");
        bulanFormat.setTimeZone(timeZone);
        tahunFormat.setTimeZone(timeZone);

        Report currReport = allReport.get(position);
        String bulan = bulanFormat.format(currReport.getTanggal());
        String tahun = tahunFormat.format(currReport.getTanggal());

        holder.month.setText(bulan);
        holder.year.setText(tahun);
        holder.jumlah.setText(String.valueOf(currReport.getJumlah()));
    }

    @Override
    public int getItemCount() {
        return allReport.size();
    }

    public void SetReports(List<Report> reports){
        allReport = reports;
        notifyDataSetChanged();
    }

    class ReportListHolder extends RecyclerView.ViewHolder{
        private TextView month;
        private TextView year;
        private TextView jumlah;

        public ReportListHolder(@NonNull View itemView) {
            super(itemView);

            month = itemView.findViewById(R.id.month_report_item);
            year = itemView.findViewById(R.id.year_report_item);
            jumlah = itemView.findViewById(R.id.jumlah_report_item);
        }
    }
}
