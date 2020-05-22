package com.kokuhaku.wonga;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.kokuhaku.wonga.model.dao.BalanceDao;
import com.kokuhaku.wonga.model.dao.DandRDao;
import com.kokuhaku.wonga.model.dao.ExpensesDao;
import com.kokuhaku.wonga.model.dao.ReportDao;
import com.kokuhaku.wonga.model.entity.Balance;
import com.kokuhaku.wonga.model.entity.DandR;
import com.kokuhaku.wonga.model.entity.Expenses;
import com.kokuhaku.wonga.model.entity.Report;
import com.kokuhaku.wonga.utils.AppUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AppRepository {
    private BalanceDao balanceDao;
    private LiveData<List<Balance>> allBalances;
    private LiveData<Integer> currentBalanceLive;

    private ExpensesDao expensesDao;
    private LiveData<List<Expenses>> allTransport;
    private LiveData<List<Expenses>> allFood;
    private LiveData<List<Expenses>> allMedical;
    private LiveData<List<Expenses>> allMisc;

    private ReportDao reportDao;
    private LiveData<List<Report>> allReports;

    private DandRDao dandRDao;
    private LiveData<List<DandR>> allDebts;
    private LiveData<List<DandR>> allReceivables;

    private int latestDandRId;

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.GetInstance(application);

        balanceDao = database.BalanceDao();
        allBalances = balanceDao.GetAllBalance();
        currentBalanceLive = balanceDao.GetCurrentBalanceLiveData();

        expensesDao = database.ExpensesDao();
        allTransport = expensesDao.GetAllTransport();
        allFood = expensesDao.GetAllFood();
        allMedical = expensesDao.GetAllMedical();
        allMisc = expensesDao.GetAllMisc();

        reportDao = database.ReportDao();
        allReports = reportDao.GetAllReport();

        dandRDao = database.DandRDao();
        allDebts = dandRDao.GetAllDebts();
        allReceivables = dandRDao.GetAllReceivables();
    }

    public void InsertIncome(Balance balance) {
        AppDatabase.databaseWriterExecutor.execute(() -> {
            int currentBalance = balanceDao.GetCurrentBalanceInteger() + balance.getJumlah();
            balance.setTotalUang(currentBalance);

            balanceDao.Inset(balance);

            Report report = reportDao.GetReport(balance.getTanggal());
            if (report != null) {
                report.setJumlah(report.getJumlah() + balance.getJumlah());
                reportDao.Update(report);
            } else {
                report = new Report(balance.getJumlah(), balance.getTanggal());
                reportDao.Insert(report);
            }
        });
    }

    public void InsertExpenses(Expenses expenses) {
        AppDatabase.databaseWriterExecutor.execute(() -> {
            expensesDao.Insert(expenses);

            String sumber;
            int tipe = expenses.getTipe();
            if (tipe == 0) {
                sumber = "Transport";
            } else if (tipe == 1) {
                sumber = "Food";
            } else if (tipe == 2) {
                sumber = "Medical";
            } else {
                sumber = "Misc";
            }

            int currentBalance = balanceDao.GetCurrentBalanceInteger() - expenses.getJumlah();

            Balance balance = new Balance(1, sumber, expenses.getJumlah(), expenses.getTanggal(), currentBalance);
            balanceDao.Inset(balance);

            Report report = reportDao.GetReport(balance.getTanggal());
            if (report != null) {
                report.setJumlah(report.getJumlah() - balance.getJumlah());
                reportDao.Update(report);
            } else {
                report = new Report(-(balance.getJumlah()), balance.getTanggal());
                reportDao.Insert(report);
            }
        });
    }

    public void InsertDandR(DandR dandR){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            dandRDao.Insert(dandR);
        });
    }

    public void PayDebt(DandR dandR){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            int currentBalance = balanceDao.GetCurrentBalanceInteger() - dandR.getJumlah();

            Balance balance = new Balance(1, "Debt", dandR.getJumlah(), AppUtils.getCurrentDateTIme(), currentBalance);
            balanceDao.Inset(balance);

            Report report = reportDao.GetReport(balance.getTanggal());
            if (report != null) {
                report.setJumlah(report.getJumlah() - balance.getJumlah());
                reportDao.Update(report);
            } else {
                report = new Report(-(balance.getJumlah()), balance.getTanggal());
                reportDao.Insert(report);
            }

            dandRDao.Delete(dandR);
        });
    }

    public void ReceiveReceivables(DandR dandR){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            int currentBalance = balanceDao.GetCurrentBalanceInteger() + dandR.getJumlah();

            Balance balance = new Balance(0, "Receivable", dandR.getJumlah(), AppUtils.getCurrentDateTIme(), currentBalance);
            balanceDao.Inset(balance);

            Report report = reportDao.GetReport(balance.getTanggal());
            if (report != null) {
                report.setJumlah(report.getJumlah() + balance.getJumlah());
                reportDao.Update(report);
            } else {
                report = new Report(balance.getJumlah(), balance.getTanggal());
                reportDao.Insert(report);
            }

            dandRDao.Delete(dandR);
        });
    }

    public Integer getLatestDandRId(){
        AppDatabase.databaseWriterExecutor.submit(new Runnable() {
            @Override
            public void run() {
                latestDandRId = dandRDao.GetLatestId();
                Log.d("DANDRID", "getLatestDandRId: " + latestDandRId);
            }
        });
        return latestDandRId;
    }

    public LiveData<Integer> getCurrentBalanceLive() {
        return currentBalanceLive;
    }

    public LiveData<List<Balance>> getAllBalances() {
        return allBalances;
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

    public LiveData<List<Report>> getAllReports() {
        return allReports;
    }

    public LiveData<List<DandR>> getAllDebts() {
        return allDebts;
    }

    public LiveData<List<DandR>> getAllReceivables() {
        return allReceivables;
    }
}
