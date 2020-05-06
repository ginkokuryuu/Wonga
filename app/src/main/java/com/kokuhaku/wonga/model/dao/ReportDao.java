package com.kokuhaku.wonga.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.kokuhaku.wonga.model.entity.Report;
import com.kokuhaku.wonga.utils.DateConverterReport;

import java.util.Date;
import java.util.List;

@Dao
public interface ReportDao {
    @Insert
    void Insert(Report report);

    @Update
    void Update(Report report);

    @Query("SELECT * FROM report_table ORDER BY tanggal DESC")
    LiveData<List<Report>> GetAllReport();

    @Query("SELECT * FROM report_table WHERE tanggal = :tanggal LIMIT 1")
    @TypeConverters(DateConverterReport.class)
    Report GetReport(Date tanggal);
}
