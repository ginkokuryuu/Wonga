package com.kokuhaku.wonga;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kokuhaku.wonga.model.dao.BalanceDao;
import com.kokuhaku.wonga.model.dao.ExpensesDao;
import com.kokuhaku.wonga.model.entity.Balance;
import com.kokuhaku.wonga.model.entity.Expenses;
import com.kokuhaku.wonga.utils.AppUtils;

import java.util.List;

public class AppRepository {
    private BalanceDao balanceDao;
    private LiveData<List<Balance>> allBalances;
    private LiveData<Integer> currentBalanceLive;

    private ExpensesDao expensesDao;
    private LiveData<List<Expenses>> allTransport;
    private LiveData<List<Expenses>> allFood;
    private LiveData<List<Expenses>> allMedical;
    private LiveData<List<Expenses>> allMisc;

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
    }

    public void InsertIncome(Balance balance){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            balanceDao.Inset(balance);
        });
    }

    public void InsertExpenses(Expenses expenses){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            expensesDao.Insert(expenses);

            String sumber;
            int tipe = expenses.getTipe();
            if(tipe == 0){
                sumber = "Transport";
            }
            else if(tipe == 1){
                sumber = "Food";
            }
            else if(tipe == 2){
                sumber = "Medical";
            }
            else{
                sumber = "Misc";
            }

            int currentBalance = currentBalanceLive.getValue() - expenses.getJumlah();
            Balance balance = new Balance(1, sumber, expenses.getJumlah(), expenses.getTanggal(), currentBalance);
        });
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
}
