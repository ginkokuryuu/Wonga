package com.kokuhaku.wonga.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kokuhaku.wonga.model.entity.Balance;

import java.util.List;

@Dao
public interface BalanceDao {
    @Insert
    void Inset(Balance balance);

    @Update
    void Update(Balance balance);

    @Query("SELECT * FROM balance_table ORDER BY tanggal DESC")
    LiveData<List<Balance>> GetAllBalance();

    @Query("SELECT totalUang FROM balance_table ORDER BY tanggal DESC LIMIT 1")
    LiveData<Integer> GetCurrentBalanceLiveData();
}
