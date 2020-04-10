package com.kokuhaku.wonga.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.kokuhaku.wonga.utils.DateConverterBalance;

import java.util.Date;

@Entity(tableName = "balance_table")
public class Balance {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int jenis;
    private String sumber;
    private int jumlah;

    @TypeConverters(DateConverterBalance.class)
    private Date tanggal;

    private int totalUang;

    public Balance(int jenis, String sumber, int jumlah, Date tanggal, int totalUang) {
        this.jenis = jenis;
        this.sumber = sumber;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.totalUang = totalUang;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getJenis() {
        return jenis;
    }

    public String getSumber() {
        return sumber;
    }

    public int getJumlah() {
        return jumlah;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public int getTotalUang() {
        return totalUang;
    }
}
