package com.kokuhaku.wonga.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.kokuhaku.wonga.utils.DateConverterBalance;

import java.util.Date;

@Entity(tableName = "dandr_table")
public class DandR {
    @PrimaryKey(autoGenerate = true)
    private int id;

    //0 hutang
    //1 piutang
    private int type;
    private String person;
    private int jumlah;

    @TypeConverters(DateConverterBalance.class)
    private Date tanggal;

    public DandR(int type, String person, int jumlah, Date tanggal) {
        this.type = type;
        this.person = person;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getPerson() {
        return person;
    }

    public int getJumlah() { return jumlah; }

    public Date getTanggal() {
        return tanggal;
    }
}
