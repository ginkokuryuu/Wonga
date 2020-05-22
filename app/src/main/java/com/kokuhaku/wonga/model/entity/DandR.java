package com.kokuhaku.wonga.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.kokuhaku.wonga.utils.DateConverterBalance;
import com.kokuhaku.wonga.utils.DateConverterNotif;

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

    @TypeConverters(DateConverterNotif.class)
    private Date dueDate;

    private int notifId;

    public DandR(int type, String person, int jumlah, Date tanggal, Date dueDate, int notifId) {
        this.type = type;
        this.person = person;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.dueDate = dueDate;
        this.notifId = notifId;
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

    public Date getDueDate() {
        return dueDate;
    }

    public int getNotifId() {
        return notifId;
    }
}
