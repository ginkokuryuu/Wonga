package com.kokuhaku.wonga.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kokuhaku.wonga.AppRepository;
import com.kokuhaku.wonga.model.entity.Report;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {
    private AppRepository appRepository;

    private LiveData<List<Report>> allReports;

    public ReportViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);

        allReports = appRepository.getAllReports();
    }

    public LiveData<List<Report>> getAllReports(){
        return allReports;
    }
}
