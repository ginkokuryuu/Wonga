package com.kokuhaku.wonga.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kokuhaku.wonga.R;
import com.kokuhaku.wonga.model.adapter.DebtListAdapter;
import com.kokuhaku.wonga.model.entity.DandR;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.utils.DateConverterNotif;
import com.kokuhaku.wonga.utils.ReminderBroadcast;
import com.kokuhaku.wonga.viewmodel.DandRViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DebtActivity extends AppCompatActivity {
    private DandRViewModel dandRViewModel;

    private BottomSheetBehavior bottomSheetBehavior;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
                DandR debtPaid = listAdapter.GetDandR(position);
                int notifId = debtPaid.getNotifId();
                Intent intent = new Intent(DebtActivity.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(DebtActivity.this, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                dandRViewModel.PayDebt(listAdapter.GetDandR(position));
            }
        });

        createNotificationChannel();

        SetBottomSheet();

        SetDatePicker();
    }

    void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "NotificationChannel";
            String description = "Channel for Notif";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("notification", name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    void SetBottomSheet(){
        TextView dueDateTv = findViewById(R.id.due_debt_tv);

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

                Date dueDate;
                try {
                    dueDate = sdf.parse(dueDateTv.getText().toString());
                    if(AppUtils.getCurrentDateTIme().compareTo(dueDate) < 0){
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        int latestId = prefs.getInt("notifId", 0);
                        latestId += 1;
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putInt("notifId", latestId);
                        edit.apply();

                        DandR newDandR = new DandR(0, personS, amountI, AppUtils.getCurrentDateTIme(), dueDate, latestId);
                        dandRViewModel.Insert(newDandR);

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        person.setText("");
                        amount.setText("");
                        dueDateTv.setText("");

                        Intent intent = new Intent(DebtActivity.this, ReminderBroadcast.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(DebtActivity.this, latestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        long timeToNotif = dueDate.getTime();
//                        timeToNotif = System.currentTimeMillis() + 10000;

                        alarmManager.set(AlarmManager.RTC_WAKEUP, timeToNotif, pendingIntent);

                        Toast.makeText(DebtActivity.this, "Notification will emerge at " + DateConverterNotif.dateToTimestamp(dueDate), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(DebtActivity.this, "Date invalid", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    void SetDatePicker(){
        TextView dueDateTv = findViewById(R.id.due_debt_tv);

        Button datePickerBtn = findViewById(R.id.date_debt_btn);
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DebtActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        dueDateTv.setText(date);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
    }
}
