package com.kokuhaku.wonga.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.adapter.FoodListAdapter;
import com.kokuhaku.wonga.model.entity.Expenses;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.viewmodel.ExpensesViewModel;

import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private ExpensesViewModel expensesViewModel;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        RecyclerView expensesRecyclerView = findViewById(R.id.food_recycle_list);
        expensesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        expensesRecyclerView.setHasFixedSize(true);

        final FoodListAdapter foodListAdapter = new FoodListAdapter();
        expensesRecyclerView.setAdapter(foodListAdapter);

        expensesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ExpensesViewModel.class);
        expensesViewModel.getAllFood().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(List<Expenses> expenses) {
                foodListAdapter.SetFood(expenses);
            }
        });
        
        SetBottomSheet();
    }

    private void SetBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet_food);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        EditText amount = findViewById(R.id.add_food_et);
        Button saveButton = findViewById(R.id.save_food_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountInt = Integer.parseInt(amount.getText().toString());
                Expenses newExpenses = new Expenses(amountInt, 1, AppUtils.getCurrentDateTIme());
                expensesViewModel.Insert(newExpenses);

                amount.setText("");
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }
}
