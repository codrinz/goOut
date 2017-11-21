package com.example.codrin.showitnow;

/**
 * Created by Codrin on 19/11/2017.
 */

public class Show{

    private String showName;
    private int day;

    public Show(String showName, int day) {
        this.showName = showName;
        this.day = day;
    }

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
        return showName+" day: "+day;
    }
}