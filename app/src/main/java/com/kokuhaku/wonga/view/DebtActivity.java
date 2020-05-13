package com.kokuhaku.wonga.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.adapter.DebtListAdapter;
import com.kokuhaku.wonga.model.entity.DandR;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.viewmodel.DandRViewModel;

import java.util.List;

public class DebtActivity extends AppCompatActivity {
    private DandRViewModel dandRViewModel;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt);

        RecyclerView recyclerView = findViewById(R.id.debt_recycle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        final DebtListAdapter listAdapter = new DebtListAdapter();
        recyclerView.setAdapter(listAdapter);

        dandRViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(DandRViewModel.class);
        dandRViewModel.getAllDebts().observe(this, new Observer<List<DandR>>() {
            @Override
            public void onChanged(List<DandR> dandRS) {
                listAdapter.SetDebtList(dandRS);
            }
        });

        listAdapter.setOnClickListener(new DebtListAdapter.onItemClickListener() {
            @Override
            public void onPayClick(int position) {
                dandRViewModel.PayDebt(listAdapter.GetDandR(position));
            }
        });

        SetBottomSheet();
    }

    void SetBottomSheet(){
        View bottomSheet = findViewById(R.id.bottom_sheet_debt);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        EditText person = findViewById(R.id.add_debt_person_et);
        EditText amount = findViewById(R.id.add_debt_amount_et);
        Button saveBtn = findViewById(R.id.save_debt_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personS = person.getText().toString();
                int amountI = Integer.parseInt(amount.getText().toString());

                DandR newDandR = new DandR(0, personS, amountI, AppUtils.getCurrentDateTIme());
                dandRViewModel.Insert(newDandR);

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                person.setText("");
                amount.setText("");
            }
        });
    }
}
