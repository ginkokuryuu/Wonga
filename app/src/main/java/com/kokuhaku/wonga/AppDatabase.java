package com.kokuhaku.wonga;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kokuhaku.wonga.model.dao.BalanceDao;
import com.kokuhaku.wonga.model.dao.ExpensesDao;
import com.kokuhaku.wonga.model.entity.Balance;
import com.kokuhaku.wonga.model.entity.Expenses;
import com.kokuhaku.wonga.utils.AppUtils;
import com.kokuhaku.wonga.utils.DateConverterBalance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Balance.class, Expenses.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 8;
    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract BalanceDao BalanceDao();
    public abstract ExpensesDao ExpensesDao();

    public static synchronized AppDatabase GetInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            AppDatabase.databaseWriterExecutor.execute(() ->{
                BalanceDao balanceDao = AppDatabase.INSTANCE.BalanceDao();
                ExpensesDao expensesDao = AppDatabase.INSTANCE.ExpensesDao();

                balanceDao.Inset(new Balance(0, "Income", 0, AppUtils.getCurrentDateTIme(), 0));

                expensesDao.Insert(new Expenses(0, 0, AppUtils.getCurrentDateTIme()));
                expensesDao.Insert(new Expenses(0, 1, AppUtils.getCurrentDateTIme()));
                expensesDao.Insert(new Expenses(0, 2, AppUtils.getCurrentDateTIme()));
                expensesDao.Insert(new Expenses(0, 3, AppUtils.getCurrentDateTIme()));
            });
        }
    };
}
