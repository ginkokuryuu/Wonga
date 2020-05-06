package com.kokuhaku.wonga.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.adapter.BalanceAdapterMain;
import com.kokuhaku.wonga.model.adapter.MiscListAdapter;
import com.kokuhaku.wonga.model.entity.Balance;
import com.kokuhaku.wonga.viewmodel.BalanceViewModel;

public class MainActivity extends AppCompatActivity {
    private BalanceViewModel balanceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView balanceRecyclerView = findViewById(R.id.balance_recycler_main);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        balanceRecyclerView.setHasFixedSize(true);

        final BalanceAdapterMain balanceAdapterMain = new BalanceAdapterMain();
        balanceRecyclerView.setAdapter(balanceAdapterMain);

        balanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BalanceViewModel.class);
        balanceViewModel.getCurrentBalanceLive().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                balanceAdapterMain.SetCurrentBalance(integer);
            }
        });

        balanceAdapterMain.setOnClickListener(new BalanceAdapterMain.onItemClickListener() {
            @Override
            public void onItemClick(int currBalance) {
                startActivity(new Intent(MainActivity.this, BalanceActivity.class));
            }
        });

        SetExpensesButton();
    }

    private void SetExpensesButton(){
        Button food = findViewById(R.id.food_main_button);
        Button transport = findViewById(R.id.transport_main_button);
        Button medical = findViewById(R.id.medical_main_button);
        Button misc = findViewById(R.id.misc_main_button);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoodActivity.class));
            }
        });

        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransportActivity.class));
            }
        });

        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MedicalActivity.class));
            }
        });

        misc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MiscActivity.class));
            }
        });
    }
}
