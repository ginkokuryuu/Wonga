package com.kokuhaku.wonga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kokuhaku.wonga.AppRepository;
import com.kokuhaku.wonga.model.entity.Expenses;

import java.util.List;

public class ExpensesViewModel extends AndroidViewModel {
    AppRepository appRepository;

    private LiveData<List<Expenses>> allTransport;
    private LiveData<List<Expenses>> allFood;
    private LiveData<List<Expenses>> allMedical;
    private LiveData<List<Expenses>> allMisc;

    public ExpensesViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);

        allTransport = appRepository.getAllTransport();
        allFood = appRepository.getAllFood();
        allMedical = appRepository.getAllMedical();
        allMisc = appRepository.getAllMisc();
    }

    public void Insert(Expenses expenses){
        appRepository.InsertExpenses(expenses);
    }

    public LiveData<List<Expenses>> getAllTransport() {
        return allTransport;
    }

    public LiveData<List<Expenses>> getAllFood() {
        return allFood;
    }

    public LiveData<List<Expenses>> getAllMedical() {
        return allMedical;
    }

    public LiveData<List<Expenses>> getAllMisc() {
        return allMisc;
    }
}
