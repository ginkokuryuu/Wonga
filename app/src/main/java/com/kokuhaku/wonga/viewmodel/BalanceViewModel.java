package com.kokuhaku.wonga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kokuhaku.wonga.AppRepository;
import com.kokuhaku.wonga.model.entity.Balance;

import java.util.List;

public class BalanceViewModel extends AndroidViewModel {
    private AppRepository appRepository;

    private LiveData<List<Balance>> allBalances;
    private LiveData<Integer> currentBalanceLive;

    public BalanceViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);

        allBalances = appRepository.getAllBalances();
        currentBalanceLive = appRepository.getCurrentBalanceLive();
    }

    public void Insert(Balance balance){
        appRepository.InsertIncome(balance);
    }

    public LiveData<List<Balance>> getAllBalances() {
        return allBalances;
    }

    public LiveData<Integer> getCurrentBalanceLive() {
        return currentBalanceLive;
    }
}
