package com.kokuhaku.wonga.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.adapter.BalanceAdapterMain;
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
    }
}
