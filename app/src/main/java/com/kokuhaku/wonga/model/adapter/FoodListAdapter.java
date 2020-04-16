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

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListHolder> {
    private List<Expenses> allFoods = new ArrayList<>();
    private static DateFormat tanggalFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat jamFormat = new SimpleDateFormat("HH:mm:ss");

    @NonNull
    @Override
    public FoodListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_item_list, parent, false);
        return new FoodListHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListHolder holder, int position) {
        Expenses currentFood = allFoods.get(position);
        TimeZone timeZone = TimeZone.getTimeZone("Etc/GMT+7");
        jamFormat.setTimeZone(timeZone);
        tanggalFormat.setTimeZone(timeZone);

        String tanggal = tanggalFormat.format(currentFood.getTanggal());
        String jam = jamFormat.format(currentFood.getTanggal());

        holder.tanggal.setText(tanggal);
        holder.jumlah.setText(String.valueOf(currentFood.getJumlah()));
        holder.jam.setText(jam);
    }

    @Override
    public int getItemCount() {
        return allFoods.size();
    }

    public void SetFood(List<Expenses> foods){
        allFoods = foods;
        notifyDataSetChanged();
    }

    class FoodListHolder extends RecyclerView.ViewHolder{
        private TextView tanggal;
        private TextView jumlah;
        private TextView jam;

        public FoodListHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tanggal_expenses_list);
            jumlah = itemView.findViewById(R.id.jumlah_expenses_list);
            jam = itemView.findViewById(R.id.jam_expenses_list);
        }
    }
}
