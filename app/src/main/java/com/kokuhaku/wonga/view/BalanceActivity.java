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
import com.kokuhaku.wonga.model.adapter.CurrentBalanceAdapter;
import com.kokuhaku.wonga.model.adapter.ReportListAdapter;
import com.kokuhaku.wonga.model.adapter.TransactionListAdapter;
import com.kokuhaku.wonga.model.entity.Balance;
import com.kokuhaku.wonga.model.entity.Report;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.viewmodel.BalanceViewModel;
import com.kokuhaku.wonga.viewmodel.ExpensesViewModel;
import com.kokuhaku.wonga.viewmodel.ReportViewModel;

import java.util.List;

public class BalanceActivity extends AppCompatActivity {
    private BalanceViewModel balanceViewModel;
    private ReportViewModel reportViewModel;

    private BottomSheetBehavior bottomSheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        RecyclerView transListRecyclerView = findViewById(R.id.list_trans_recycler_balance);
        transListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        transListRecyclerView.setHasFixedSize(true);

        RecyclerView currBalanceRecyclerView = findViewById(R.id.curr_balance_recycler_balance);
        currBalanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        currBalanceRecyclerView.setHasFixedSize(true);

        RecyclerView reportListRecyclerView = findViewById(R.id.report_recycler_view);
        reportListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        reportListRecyclerView.setHasFixedSize(true);

        final TransactionListAdapter transactionListAdapter = new TransactionListAdapter();
        transListRecyclerView.setAdapter(transactionListAdapter);

        final CurrentBalanceAdapter currentBalanceAdapter = new CurrentBalanceAdapter();
        currBalanceRecyclerView.setAdapter(currentBalanceAdapter);

        final ReportListAdapter reportListAdapter = new ReportListAdapter();
        reportListRecyclerView.setAdapter(reportListAdapter);

        balanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BalanceViewModel.class);
        balanceViewModel.getAllBalances().observe(this, new Observer<List<Balance>>() {
            @Override
            public void onChanged(List<Balance> balances) {
                transactionListAdapter.SetBalances(balances);
                currentBalanceAdapter.SetBalances(balances);
            }
        });

        reportViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(ReportViewModel.class);
        reportViewModel.getAllReports().observe(this, new Observer<List<Report>>() {
            @Override
            public void onChanged(List<Report> reports) {
                reportListAdapter.SetReports(reports);
            }
        });

        SetBottomSheet();
    }

    void SetBottomSheet(){
        View bottomSheet = findViewById(R.id.bottom_sheet_balance);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        EditText amount = findViewById(R.id.add_income_et);
        Button saveButton = findViewById(R.id.save_income_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().trim().length() > 0){
                    int amountInt = Integer.parseInt(amount.getText().toString());
                    Balance newBalance = new Balance(0, "Income", amountInt, AppUtils.getCurrentDateTIme(), 0);
                    balanceViewModel.Insert(newBalance);

                    amount.setText("");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }
}
