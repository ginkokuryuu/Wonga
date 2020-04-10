package com.kokuhaku.wonga.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.kokuhaku.wonga.utils.DateConverterExpenses;

import java.util.Date;

@Entity(tableName = "expenses_table")
public class Expenses {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int tipe;
    private int jumlah;

    @TypeConverters(DateConverterExpenses.class)
    private Date tanggal;

    public Expenses(int jumlah, int tipe, Date tanggal) {
        this.jumlah = jumlah;
        this.tipe = tipe;
        this.tanggal = tanggal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getTipe() {
        return tipe;
    }

    public Date getTanggal() {
        return tanggal;
    }
}
