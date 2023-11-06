package com.example.noteapp.ModelClass;

//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_Table")
public class RoomModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "tittle")
    private String tittle;

    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "time")
    private String time;

    public RoomModel(int id, String tittle, String content, String time) {
        this.id = id;
        this.tittle = tittle;
        this.content = content;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Ignore
    public RoomModel(String tittle, String content, String time) {
        this.tittle = tittle;
        this.content = content;
        this.time = time;
    }
}
