package com.example.codrin.showitnow.utils.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Codrin on 19/11/2017.
 */
@Entity(tableName = "Shows")
public class Show{

    @PrimaryKey(autoGenerate = true)
    private int sid;
    @ColumnInfo(name = "show_name")
    private String showName;
    @ColumnInfo(name = "day")
    private int day;
    @ColumnInfo(name = "month")
    private int month;
    @ColumnInfo(name = "free_spots")
    private int freeSpots;
    @ColumnInfo(name = "all_spots")
    private int allSpots;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(int freeSpots) {
        this.freeSpots = freeSpots;
    }

    public int getAllSpots() {
        return allSpots;
    }

    public void setAllSpots(int allSpots) {
        this.allSpots = allSpots;
    }
    @Ignore
    public Show(int sid, String showName, int day, int month, int freeSpots, int allSpots) {

        this.sid = sid;
        this.showName = showName;
        this.day = day;
        this.month = month;
        this.freeSpots = freeSpots;
        this.allSpots = allSpots;
    }
    @Ignore
    public Show(String showName, int day, int month, int freeSpots, int allSpots) {
        this.showName = showName;
        this.day = day;
        this.month = month;
        this.freeSpots = freeSpots;
        this.allSpots = allSpots;
    }

    @Ignore
    public Show(String showName, int day) {
        this.showName = showName;
        this.day = day;
    }

    @Ignore
    public Show(Show show){
        this.sid = show.getSid();
        this.showName = show.getShowName();
        this.day = show.getDay();
        this.month = show.getMonth();
        this.freeSpots = show.getFreeSpots();
        this.allSpots = show.getAllSpots();
    }

    public Show(){}
    
    public String getShowName(){
        return showName;
    }
    public void changeShowName(String newName){
        showName = newName;
    }

    public int getDay(){
        return day;
    }

    public void changeShowDate(int day){
        this.day = day;
    }//changes only the day of the week

    @Override
    public String toString() {
        return showName+" day: "+day+" month: "+month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (sid != show.sid) return false;
        if (day != show.day) return false;
        if (month != show.month) return false;
        if (freeSpots != show.freeSpots) return false;
        if (allSpots != show.allSpots) return false;
        return showName.equals(show.showName);
    }

    @Override
    public int hashCode() {
        int result = sid;
        result = 31 * result + showName.hashCode();
        result = 31 * result + day;
        result = 31 * result + month;
        result = 31 * result + freeSpots;
        result = 31 * result + allSpots;
        return result;
    }
}