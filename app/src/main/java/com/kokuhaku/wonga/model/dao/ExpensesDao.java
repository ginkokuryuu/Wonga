package com.kokuhaku.wonga.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kokuhaku.wonga.model.entity.Expenses;

import java.util.List;

@Dao
public interface ExpensesDao {
    @Insert
    void Insert(Expenses expenses);

    @Query("SELECT * FROM expenses_table WHERE tipe = 0 ORDER BY tanggal DESC")
    LiveData<List<Expenses>> GetAllTransport();

    @Query("SELECT * FROM expenses_table WHERE tipe = 1 ORDER BY tanggal DESC")
    LiveData<List<Expenses>> GetAllFood();

    @Query("SELECT * FROM expenses_table WHERE tipe = 2 ORDER BY tanggal DESC")
    LiveData<List<Expenses>> GetAllMedical();

    @Query("SELECT * FROM expenses_table WHERE tipe = 3 ORDER BY tanggal DESC")
    LiveData<List<Expenses>> GetAllMisc();
}
