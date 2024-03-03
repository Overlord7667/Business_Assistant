package com.betelgeuse.corp.businessassistant.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.betelgeuse.corp.businessassistant.R;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "notes")
public class Notes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    @ColumnInfo(name = "title")
    String title = "";

    @ColumnInfo(name = "notes")
    String notes = "";

    @ColumnInfo(name = "date")
    String date = "";

//    @ColumnInfo(name = "alarm_time")
//    private String alarmTime;

//    public String getAlarmTime() {
//        return alarmTime;
//    }

//    public void setAlarmTime(String alarmTime) {
//        this.alarmTime = alarmTime;
//    }

//    public Notes(String alarmTime) {
//        this.alarmTime = alarmTime;
//    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Notes other = (Notes) obj;
        return ID == other.ID && Objects.equals(title, other.title) && Objects.equals(notes, other.notes) && Objects.equals(date, other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, title, notes, date);
    }

}