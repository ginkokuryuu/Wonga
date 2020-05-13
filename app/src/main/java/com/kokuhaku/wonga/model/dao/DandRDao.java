package com.kokuhaku.wonga.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kokuhaku.wonga.model.entity.DandR;

import java.util.List;

@Dao
public interface DandRDao {
    @Insert
    void Insert(DandR dandR);

    @Update
    void Update(DandR dandR);

    @Delete
    void Delete(DandR dandR);

    @Query("SELECT * FROM dandr_table WHERE type = 0 ORDER BY tanggal ASC")
    LiveData<List<DandR>> GetAllDebts();

    @Query("SELECT * FROM dandr_table WHERE type = 1 ORDER BY tanggal ASC")
    LiveData<List<DandR>> GetAllReceivables();

}
