package com.kokuhaku.wonga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kokuhaku.wonga.AppRepository;
import com.kokuhaku.wonga.model.entity.DandR;

import java.util.List;

public class DandRViewModel extends AndroidViewModel {
    AppRepository appRepository;

    LiveData<List<DandR>> allDebts;
    LiveData<List<DandR>> allReceivables;

    public DandRViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);

        allDebts = appRepository.getAllDebts();
        allReceivables = appRepository.getAllReceivables();
    }

    public void Insert(DandR dandR){
        appRepository.InsertDandR(dandR);
    }

    public void PayDebt(DandR dandR){
        appRepository.PayDebt(dandR);
    }

    public void ReceiveReceivable(DandR dandR){
        appRepository.ReceiveReceivables(dandR);
    }

    public LiveData<List<DandR>> getAllDebts() {
        return allDebts;
    }

    public LiveData<List<DandR>> getAllReceivables() {
        return allReceivables;
    }

    public Integer GetLastId(){
        return appRepository.getLatestDandRId();
    }
}
