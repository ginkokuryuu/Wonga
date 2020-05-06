package com.kokuhaku.wonga.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.kokuhaku.wonga.utils.DateConverterReport;

import java.util.Date;

@Entity(tableName = "report_table")
public class Report {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int jumlah;

    @TypeConverters(DateConverterReport.class)
    private Date tanggal;

    public Report(int jumlah, Date tanggal) {
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getId() {
        return id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public Date getTanggal() {
        return tanggal;
    }
}
