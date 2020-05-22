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
import com.kokuhaku.wonga.model.adapter.DebtListAdapter;
import com.kokuhaku.wonga.model.adapter.ReceivableListAdapter;
import com.kokuhaku.wonga.model.entity.DandR;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.viewmodel.DandRViewModel;

import java.util.List;

public class ReceivableActivity extends AppCompatActivity {
    private DandRViewModel dandRViewModel;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivable);

        RecyclerView recyclerView = findViewById(R.id.receive_recycle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        final ReceivableListAdapter listAdapter = new ReceivableListAdapter();
        recyclerView.setAdapter(listAdapter);

        dandRViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(DandRViewModel.class);
        dandRViewModel.getAllReceivables().observe(this, new Observer<List<DandR>>() {
            @Override
            public void onChanged(List<DandR> dandRS) {
                listAdapter.SetReceivableList(dandRS);
            }
        });

        listAdapter.setOnClickListener(new ReceivableListAdapter.onItemClickListener() {
            @Override
            public void onPaidClick(int position) {
                dandRViewModel.ReceiveReceivable(listAdapter.GetDandR(position));
            }
        });

        SetBottomSheet();
    }

    void SetBottomSheet(){
        View bottomSheet = findViewById(R.id.bottom_sheet_receive);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        EditText person = findViewById(R.id.add_receive_person_et);
        EditText amount = findViewById(R.id.add_receive_amount_et);
        Button saveBtn = findViewById(R.id.save_receive_btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personS = person.getText().toString();
                int amountI = Integer.parseInt(amount.getText().toString());

                DandR newDandR = new DandR(1, personS, amountI, AppUtils.getCurrentDateTIme(), AppUtils.getCurrentDateTIme(), 0);
                dandRViewModel.Insert(newDandR);

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                person.setText("");
                amount.setText("");
            }
        });
    }
}
