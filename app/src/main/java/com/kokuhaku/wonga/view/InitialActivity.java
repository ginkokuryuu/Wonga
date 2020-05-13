package com.kokuhaku.wonga.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.entity.Balance;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.viewmodel.BalanceViewModel;

public class InitialActivity extends AppCompatActivity {
    BalanceViewModel balanceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        balanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BalanceViewModel.class);

        EditText amount = findViewById(R.id.init_balance_et);
        Button saveBtn = findViewById(R.id.init_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amountI = Integer.parseInt(amount.getText().toString());
                Balance newBalance = new Balance(0, "Initial Balance", amountI, AppUtils.getCurrentDateTIme(), amountI);
                balanceViewModel.Insert(newBalance);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean previouslyStarted = prefs.getBoolean("started", false);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putBoolean("started", Boolean.TRUE);
                edit.apply();
                finish();
            }
        });
    }
}
